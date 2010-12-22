/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.maven2deploytests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author mirko
 * 
 */
public class InvokeReleaseBuild implements Runnable {

    private final Properties properties = new Properties();

    private final File releaseProperties;

    /**
     * 
     */
    public InvokeReleaseBuild() {
        this(new File("release.properties"));
    }

    /**
     * 
     */
    public InvokeReleaseBuild(final File releaseProperties) {
        this.releaseProperties = releaseProperties;
        if (!releaseProperties.exists()) {
            throw new IllegalArgumentException("Could not find " + releaseProperties);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new InvokeReleaseBuild().run();

    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        readProperties();
        final UrlEncodedFormEntity entity = newUrlEncodedFormEntity();
        final String string;
        try {
            string = EntityUtils.toString(entity);
            System.out.println(string);
        } catch (ParseException e) {
            throw new RuntimeException("Message:", e);
        } catch (IOException e) {
            throw new RuntimeException("Message:", e);
        }        
    }

    /**
     * @return
     */
    UrlEncodedFormEntity newUrlEncodedFormEntity() {
        final List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(properties.size());
        final Set<Entry<Object, Object>> entrySet = properties.entrySet();
        for (Entry<Object, Object> entry : entrySet) {
            list.add(new BasicNameValuePair("m2release." + (String) entry.getKey(), (String) entry.getValue()));
        }        
        final UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(list);            
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Message:", e);
        }
        return entity;
    }

    /**
     * 
     */
    void readProperties() {
        final FileReader reader;
        try {
            reader = new FileReader(releaseProperties);
            try {
                properties.load(reader);
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Message:", e);
        } catch (IOException e) {
            throw new RuntimeException("Message:", e);
        }
    }

}
