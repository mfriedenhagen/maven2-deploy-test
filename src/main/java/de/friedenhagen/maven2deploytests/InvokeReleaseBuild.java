/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.maven2deploytests;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author mirko
 *
 */
public class InvokeReleaseBuild implements Runnable {

    
    /**
     * @param args
     */
    public static void main(String[] args) {
        new InvokeReleaseBuild().run();

    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder;
        final Document document;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(new File("pom.xml"));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Message:", e);
        } catch (SAXException e) {
            throw new RuntimeException("Message:", e);
        } catch (IOException e) {
            throw new RuntimeException("Message:", e);
        }
        System.out.println(document.getElementsByTagName("project").item(0));
    }

}
