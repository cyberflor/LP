/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lbplanet.labelling;

import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter; 
import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.UnsupportedEncodingException;
public class QRcode {
    public static String main(String[] args) {
        try {
            String qrCodeData = "www.chillyfacts.com";
            String filePath = "D:\\QRCODE\\chillyfacts.png";
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map < EncodeHintType, ErrorCorrectionLevel > hintMap = new HashMap < EncodeHintType, ErrorCorrectionLevel > ();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                new String(qrCodeData.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, 200, 200, hintMap);
            //MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
            //    .lastIndexOf('.') + 1), new File(filePath));
            return "QR Code image created successfully!";
        } catch (WriterException | UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}

