package com.takeoff.service;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;


@Service
public class LogoService {
	
	    public BufferedImage trim(BufferedImage img) {
	        int width  = getTrimmedWidth(img);
	        int height = getTrimmedHeight(img);

	        BufferedImage newImg = new BufferedImage(width, height,
	                BufferedImage.TYPE_INT_RGB);
	        Graphics g = newImg.createGraphics();
	        g.drawImage( img, 0, 0, null );
	        img = newImg;

	AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
	tx.translate(-img.getWidth(null), -img.getHeight(null));
	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	img = op.filter(img, null);
	
	return img;


	    }

	    
	    public int solution(int[] A) {
	        
	        List aList=Arrays.asList(A);
	         Integer max = (Integer) Collections.max(aList);
	         System.out.println(max);
	        for(int i=max;i>=1;i--)
	        {
	            System.out.println("asdf");
	        }

	return max;
	    }

	    private int getTrimmedWidth(BufferedImage img) {
	        int height       = img.getHeight();
	        int width        = img.getWidth();
	        int trimmedWidth = 0;
	        int removeColor = img.getRGB(width-1, height-1);
	        for(int i = 0; i < height; i++) {
	            for(int j = width - 1; j >= 0; j--) {
	                if(img.getRGB(j, i) != removeColor &&
	                        j > trimmedWidth) {
	                    trimmedWidth = j;
	                    break;
	                }
	            }
	        }
	        
	        System.out.println(trimmedWidth);


	        return trimmedWidth;
	    }

	    private int getTrimmedHeight(BufferedImage img) {
	        int width         = img.getWidth();
	        int height        = img.getHeight();
	        int trimmedHeight = 0;

	        int removeColor = img.getRGB(width-1, height-1);
	        
	        for(int i = 0; i < width; i++) {
	            for(int j = height - 1; j >= 0; j--) {
	                if(img.getRGB(i, j) != removeColor &&
	                        j > trimmedHeight) {
	                    trimmedHeight = j;
	                    break;
	                }
	            }
	        }
	        System.out.println(trimmedHeight);
	        return trimmedHeight;
	    }

	    public String createImage(String htmlData, Boolean logo) throws IOException {
	    	
	    	System.out.println(new java.util.Date());
	    	 byte[] pdfBytes = null;
	    	  try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); BufferedOutputStream bufOs = new BufferedOutputStream(bos)) {
	              final PdfRendererBuilder pdfBuilder = new PdfRendererBuilder();
	              //pdfBuilder.useDefaultPageSize(660, 320, PdfRendererBuilder.PageSizeUnits.MM);
	              //pdfBuilder.usePdfAConformance(PdfRendererBuilder.PdfAConformance.PDFA_3_A);
	              pdfBuilder.useFastMode();
	              pdfBuilder.withHtmlContent(htmlData, "");
	              pdfBuilder.toStream(bufOs);
	              pdfBuilder.run();
	              pdfBytes = bos.toByteArray();
	              bos.close();
	              bufOs.close();
	          }
	    	 
	    
	       
	       
	       
	        PDDocument pd = PDDocument.load (pdfBytes);
	        PDFRenderer pr = new PDFRenderer (pd);
	        BufferedImage img = pr.renderImageWithDPI (0, 300);
	        
	       
	       


	    

	       
	for(int i=0;i<2;i++)
	{
	        img = trim(img);
	       
	}
	
	if(!logo)
        img =  Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT,  660,320);
		
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	ImageIO.write(img,"JPEG",bos);
	
	String image =   new String(Base64.encodeBase64(bos.toByteArray()), "UTF-8");
	bos.close();  
	pd.close();
	System.out.println(new java.util.Date());
	return image;
	
	    }
	
}
