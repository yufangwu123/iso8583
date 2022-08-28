package com.wt.iso8583;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import com.wt.iso8583.bean.EncodeType;
import com.wt.iso8583.bean.Iso8583Entity;
import com.wt.iso8583.bean.LengthType;
import com.wt.iso8583.utils.BitArray;
import com.wt.iso8583.utils.ByteUtils;
import com.wt.iso8583.utils.Coder;
import com.wt.iso8583.utils.Converts;

public class Iso8583Utils {
	
	//�õ�i��0
	private String getZero(int i) {
		if (i == 0) {
			return "";
		}
		return String.format("%" + i + "s", "0").replace(" ", "0");
	}
	
	/**
	 * ��λ
	 * 
	 * @param output
	 */
	private void addZero(ByteArrayOutputStream output, int num) {
		for (int i = 0; i < num; i++) {
			output.write(0);
		}
	}
	
	/**
	 * ת��8583����
	 * 
	 * @return 2�ֽڳ���+ 11�ֽ�TPDU + ������(ָ���� + λͼ + ��)
	 * @throws IOException
	 */
	public byte[] packageIso8583Datas(Map<Integer, String> content,boolean isUseExtendedBitMap) throws IOException {
		int bitMapBitCount=64;
		if (isUseExtendedBitMap) {
			bitMapBitCount=128;
		}
		// ��װ BitArray��  1.����λͼ���õ����� 2.����λͼ����������
		BitArray bitmap = new BitArray(bitMapBitCount);
		//���屨���� ByteArrayOutputStream������װ��  ����(ָ���� + λͼ + ��) ����
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		//���������� ByteArrayOutputStream ����װ�������ݣ�����򳤶�����Ϊ����������������Ϊ���򳤶�+����������ݣ�
		ByteArrayOutputStream data = new ByteArrayOutputStream(); 
		for (Map.Entry<Integer, String> entry : content.entrySet()) {
			//1����д��ָ���� ������
			if (entry.getKey() == 0) { 
				output.write(ByteUtils.getByteByNoSpli(entry.getValue()));
				continue;
			}
			int len = entry.getValue().length();// ��ȡ���ݳ���
			Iso8583Entity entity = DomainInfoForm.iso.get(entry.getKey().intValue() - 1); // ��ȡ��������
			//2������λͼ����
			bitmap.set(entry.getKey().intValue() - 1, true); 
			String value = entry.getValue();
			//3�����������ݳ���
			if (entity.getLengthType() == LengthType.FIX_LEN) { // ��������ǹ̶���
				len = entity.getLength();
				if (entity.getEncodeType() == EncodeType.L_BCD) {
					int zero = len - entry.getValue().length();
					value = entry.getValue() + getZero(zero);
				} else if (entity.getEncodeType() == EncodeType.R_BCD) {
					int zero = len - entry.getValue().length();
					value = getZero(zero) + entry.getValue();
				}
			} else if (entity.getLengthType() == LengthType.LLVAR_LEN) {//3.1�����Ϊ����������д�������ݳ��ȣ�0-99Ϊ1���ֽ��򳤶�
				if (entity.getEncodeType()==EncodeType.BINARY) {
					data.write(Converts.strToBcdByNum(len/2 + "", 1));
				}else{
					data.write(Converts.strToBcdByNum(len + "", 1));
				}
			} else if (entity.getLengthType() == LengthType.LLLVAR_LEN) {//3.1�����Ϊ����������д�������ݳ��ȣ�0-999Ϊ2���ֽ��򳤶�
				// data.write(ByteUtils.intToByte(len, 2));
				if (entity.getEncodeType()==EncodeType.BINARY) {
					data.write(Converts.strToBcdByNum(len/2 + "", 2));
				}else{
					data.write(Converts.strToBcdByNum(len + "", 2));
				}
			}
			//4�� ����������
			int entryLength = value.getBytes().length;
			switch (entity.getEncodeType()) {
				case L_BCD: /* �����BCD�� */
					data.write(Converts.strToBcd(value, 1));// ת��Ϊbcd
					addZero(data, len - entryLength);
					break;
				case L_ASC: /* �����ASC�� */
					data.write(value.getBytes());
					addZero(data, len - entryLength);
					break;
				case R_BCD: /* �Ҷ���BCD�� */
					addZero(data, len - entryLength);
					data.write(Converts.strToBcd(value, 2));// ת��Ϊbcd
					break;
				case R_ASC: /* �Ҷ���ASC�� */
					addZero(data, len - entryLength);
					data.write(value.getBytes());
					break;
				case BINARY: /* �Ҷ���ASC�� */
					addZero(data, len - entryLength);
	//				data.write(value.getBytes());
					data.write(ByteUtils.getByteByNoSpli(value));
					break;
				default:
					break;
			}
	
		}
		//5��д��λͼ����
		output.write(bitmap.toByteArray());
		//6��д��ƴ�Ӻõ�������(�������������򳤶�+����������)
		output.write(data.toByteArray());
		//7�������ܰ��ĳ���
		int resultLen = output.toByteArray().length + 11;
		byte[] lengthRes = { (byte) ((resultLen >> 8) & 0xFF),
				(byte) ((resultLen) & 0xFF) };
		//����װ�� �ܰ��� ByteArrayOutputStream 2�ֽڳ���+ 11�ֽ�TPDU + ������(ָ���� + λͼ + ��) =
		ByteArrayOutputStream result = new ByteArrayOutputStream();	
		//8��д�볤��
		result.write(lengthRes);
		byte[] tpdu = { 0x60, 0x00, 0x00, 0x00, 0x03, 0x60, 0x31, 0x00, 0x11,
				0x43, 0x00 };
		//9��д��tpdu
		result.write(tpdu);
		//10��д�����λͼ����
		result.write(output.toByteArray());
		System.out.println("��װ�ı���:\n"
				+ ByteUtils.getHexStr(result.toByteArray()));
		return result.toByteArray();
	}
	
	/**
	 * ����8583���ݰ�
	 * data = ������(ָ����(0�� 2�ֽ�)+λͼ(8�ֽ�)+����������
	 * Map<Integer, String> = ���������� keyΪ���ţ�valueΪ�����ŵ�����
	 * */
	public Map<Integer, String> parseIso8583Datas(byte[] data) {
		//�����򼯺�
		Map<Integer, String> mp = new HashMap<Integer, String>();
		//�������ڷ������data��ByteArrayInputStream����
		ByteArrayInputStream datas = new ByteArrayInputStream(data);
		int bitMapByteCount=8;
		//1���ж��Ƿ�ʹ����չλͼ
		if(((data[0]) & 128) !=0) {
			bitMapByteCount=16;//��ʾ��һλ��1��ʹ����չλͼ��λͼ���ֽ���Ϊ16�ֽ�
		}
		byte[] bitmapArray = new byte[bitMapByteCount];
		//2�� �ȶ�ȡ2�ֽ�ָ����������
		//datas.read(bitmapArray, 0, 2);
		//3���ٶ�ȡλͼ����
		datas.read(bitmapArray, 0, bitMapByteCount);
		int bitMapBitCount=bitMapByteCount*8;//1��byte=8bit,����λͼ��bit��
		//4����װ BitArray��  1.����λͼ���õ����� 2.����λͼ����������
		BitArray bitmap = new BitArray(bitMapBitCount, bitmapArray);// λͼ
		for (int i = 0; i < bitMapBitCount; i++) { // ����λͼ
            if(i % 64 == 0){
            	continue;
			}
			boolean bitHave = bitmap.get(i);
			if (bitHave) {
				//5���õ���׼��ʵ����Ϣ�������ھ���ÿ�����������
				System.out.println(i+1);
				Iso8583Entity entity = DomainInfoForm.iso.get(i);
				LengthType lentype = entity.getLengthType();
				int len = 0;
				int oldLen = 0;
				//5.1������   ��׼�򳤶�����   �õ�   ���������ݳ���
				switch (lentype) {
					case FIX_LEN://����Ƕ���
						len = entity.getLength();
						break;
					case LLVAR_LEN://����ǲ����������ȷ�ΧΪ��0-99��
						byte[] ll = new byte[2];//�򳤶�Ϊ1���ֽ�
						try {
							datas.read(ll);//��ȡ1���ֽ��򳤶�
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						len = ByteUtils.bytesToIntTwo(Coder.EBCDICToASCII(ll));//����ת��
						break;
					case LLLVAR_LEN://����ǲ����������ȷ�ΧΪ��0-999��
						byte[] lll = new byte[3];//�򳤶�Ϊ2���ֽ�
						try {
							datas.read(lll);//��ȡ2���ֽ��򳤶�
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						len = ByteUtils.bytesToIntTwo(Coder.EBCDICToASCII(lll));//����ת��
						break;
					default:
						break;
				}
				//5.2 ���� ��׼��������� �������� ���н���ת��
				EncodeType patternType = entity.getEncodeType();
				byte[] contentByte = new byte[len];
				//��ȡ5.1�м���������ֽڳ��ȴ�С��������
				datas.read(contentByte, 0, len);
				String contentStr = "";
				contentStr = new String(Coder.EBCDICToASCII(contentByte));
				mp.put(i + 1, contentStr);
			}
		}
		return mp;
	}

}
