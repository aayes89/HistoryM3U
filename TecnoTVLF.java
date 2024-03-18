package tecnotvlf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TecnoTVLF {

    public static void main(String[] args) {
        try {
            String mainUrl = "https://spinoff.link/listas-premium/";
            URL url = new URL(mainUrl);
            URLConnection connection = url.openConnection();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("m3u")) {
                        downloadM3U(line);
                    }
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(TecnoTVLF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TecnoTVLF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void downloadM3U(String url) {
        try {
            String extractedUrl = extractURL(url);
            URL m3uUrl = new URL(extractedUrl);
            URLConnection connection = m3uUrl.openConnection();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("History")) {
                        System.out.println(line);
                        line = br.readLine();
                        System.out.println(line);
                    }
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(TecnoTVLF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TecnoTVLF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static String extractURL(String line) {
        String extracted = "";
        Pattern pattern = Pattern.compile("value=\"(.*?)\"");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            extracted = matcher.group(1);
        }
        return extracted;
    }
}
