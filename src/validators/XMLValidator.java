package validators;
/**
 * Created with IntelliJ IDEA.
 * User: Neha
 * Date: 10/4/14
 * Time: 9:48 PM
 */

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class XMLValidator {
    private static boolean validateXMLxsd(String path, String xmlPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(path));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException e) {
            System.out.println("Error in validating : " + xmlPath + " : \n" + e.getMessage());
            return false;
        } catch (SAXException e) {
            System.out.println("Error in validating: " + xmlPath + " : \n" + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error in validating: " + xmlPath + " : \n" + e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean validateXMLdtd(String dtdFile) {
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw new SAXException(exception);
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw new SAXException(exception);
                }

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    throw new SAXException(exception);
                }
            });
            Document doc = builder.parse(dtdFile);
        } catch (IOException e) {
            System.out.println("Error in validating: " + dtdFile + " : \n" + e.getMessage());
            return false;
        } catch (SAXException e) {
            System.out.println("Error in validating: " + dtdFile + " : \n" + e.getMessage());
            return false;
        } catch (ParserConfigurationException e) {
            System.out.println("Error in validating: " + dtdFile + " : \n" + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error in validating: " + dtdFile + " : \n" + e.getMessage());
            return false;
        }
        return true;
    }

  
    public static boolean validateXMLFromXSD(String xmlFile, String xsdSchemaFile) {
      return validateXMLxsd(xsdSchemaFile, xmlFile);
    }
    
    public static boolean validateXMLFromDTD(String xmlFile) {
    	return validateXMLdtd(xmlFile);
    }
}
