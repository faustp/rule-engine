/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.test.data;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import wrs.common.murlin._MURLinURLJSONObj;
import wrs.common.murlin._MURLinNormalizeURLJSONObj;
import wrs.common.murlin.GenericMURLinJSONObject;

/**
 *
 * @author roelr
 */
public class DataGenerator {

    public Gson gson = new Gson();

    public GenericMURLinJSONObject generateMURLinJSONObject(String srcName, _MURLinURLJSONObj url) {
        GenericMURLinJSONObject murlinObj = new GenericMURLinJSONObject();
        murlinObj.setSrcCHName(srcName);
        murlinObj.setUrl(url);
        return murlinObj;
    }

    public String generateMURLinJSONObjectString(String srcName, _MURLinURLJSONObj url) {
        GenericMURLinJSONObject murlinObj = new GenericMURLinJSONObject();
        murlinObj.setSrcCHName(srcName);
        murlinObj.setUrl(url);
        return gson.toJson(murlinObj, wrs.common.murlin.GenericMURLinJSONObject.class);
    }

    public _MURLinURLJSONObj generateURL(String rawURL) {
        return generateURL(rawURL, new _MURLinNormalizeURLJSONObj(), null);
    }

    public _MURLinURLJSONObj generateURL(String rawURL, _MURLinNormalizeURLJSONObj normalizedURL) {
        return generateURL(rawURL, normalizedURL, null);
    }

    public _MURLinURLJSONObj generateURL(String rawURL, _MURLinNormalizeURLJSONObj normalizedURL, String resolvedIP) {
        _MURLinURLJSONObj _url = new _MURLinURLJSONObj();
        _url.setNorm(normalizedURL);
        _url.setRaw(rawURL);
        _url.setResolved_ip(resolvedIP);
        return _url;
    }

    public ArrayList<String> fetchSampleSet(int sample_set) throws Exception {
        int i = 0;
        ArrayList<String> urls = new ArrayList<String>();
        BufferedReader read = new BufferedReader(new InputStreamReader(DataGenerator.class.getResourceAsStream("test001.txt")));
        String line = null;
        while ((line = read.readLine()) != null) {
            if (line != null) {
                if (!line.isEmpty()) {
                    i++;
                    if (i <= sample_set) {
                        urls.add(line.trim());
                    } else {
                        break;
                    }
                }
            }
        }
        read.close();
        read = null;
        return urls;
    }
}
