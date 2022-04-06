package org.me.gcu.labstuff.androidcwk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class trafficInfoParser  extends AppCompatActivity
    {

        private String result = "";
        private String url1="";
        // Traffic Scotland Planned Roadworks XML link
        private String urlSource="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
        private String urlSource2 = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
        private String urlSource3 = "https://trafficscotland.org/rss/feeds/roadworks.aspx";


        public void startProgress()
        {
            // Run network access on a separate thread;

                new Thread(new Task(urlSource)).start();


        } //




        // Need separate thread to access the internet resource over network
        // Other neater solutions should be adopted in later iterations.
        class Task implements Runnable
        {
            private String url;

            public Task(String aurl)
            {
                url = aurl;
            }
            @Override
            public void run()
            {

                URL aurl;
                URLConnection yc;
                BufferedReader in = null;
                String inputLine = "";


                Log.e("MyTag","in run");

                try
                {
                    Log.e("MyTag","in try");
                    aurl = new URL(url);
                    yc = aurl.openConnection();
                    in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                    Log.e("MyTag","after ready");
                    //
                    // Now read the data. Make sure that there are no specific hedrs
                    // in the data file that you need to ignore.
                    // The useful data that you need is in each of the item entries
                    //
                    while ((inputLine = in.readLine()) != null)
                    {
                        result = result + inputLine;
                        Log.e("MyTag",inputLine);

                    }
                    in.close();
                }
                catch (IOException ae)
                {
                    Log.e("MyTag", "ioexception in run");
                }

                //
                // Now that you have the xml data you can parse it
                //
  parseData(result);
                // Now update the TextView to display raw XML data
                // Probably not the best way to update TextView
                // but we are just getting started !

            }

        }

        private void parseData(String dataToParse)
        {   ArrayList<trafficInfo> trafficInfos = new ArrayList<>();
            trafficInfo tInfo = new trafficInfo();
            try
            {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( new StringReader( dataToParse ) );
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    // Found a start tag
                    if(eventType == XmlPullParser.START_TAG)
                    {
                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            // Now just get the associated text
                            String title = xpp.nextText();
                            // Do something with text
                            tInfo.setTitle(title);
                        }
                        else
                            // Check which Tag we have
                            if (xpp.getName().equalsIgnoreCase("description"))
                            {
                                // Now just get the associated text
                                String desc = xpp.nextText();
                                // Do something with text
                                tInfo.setDescription(desc);
                            }
                            else
                                // Check which Tag we have
                                if (xpp.getName().equalsIgnoreCase("link"))
                                {
                                    // Now just get the associated text
                                    String link = xpp.nextText();
                                    // Do something with text
                                    tInfo.setLink(link);
                                }
                                else
                                    // Check which Tag we have
                                    if (xpp.getName().equalsIgnoreCase("georss:point"))
                                    {
                                        // Now just get the associated text
                                        String georsslatandlon = xpp.nextText();
                                        String [] latandlon = georsslatandlon.split(" ");
                                        // Do something with text
                                        tInfo.setGeorsslat(Float.parseFloat(latandlon[0]));
                                        tInfo.setGeorsslon(Float.parseFloat(latandlon[1]));
                                    }
                                    else
                                        // Check which Tag we have
                                        if (xpp.getName().equalsIgnoreCase("pubDate"))
                                        {
                                            // Now just get the associated text
                                            String pubDate = xpp.nextText();
                                            // Do something with text
                                            tInfo.setPubDate(pubDate);
                                        }
                        trafficInfos.add(tInfo);
                    }

                    // Get the next event
                    eventType = xpp.next();

                } // End of while

            }
            catch (XmlPullParserException ae1)
            {
                Log.e("MyTag","Parsing error" + ae1.toString());
            }
            catch (IOException ae1)
            {
                Log.e("MyTag","IO error during parsing");
            }

            Log.e("MyTag","End document");



        }




    }

