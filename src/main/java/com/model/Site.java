package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Created by guitarnut on 12/9/17.
 */
@Document
public class Site {
    @Id
    private String id;
    private String name;
    private String url;
    private String selector;
    private String cookiename;
    private String cookievalue;
    private String useragent;
    private int timeout;
    private Map<String, String> headers;
    private String referrer;
    private int enabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getCookiename() {
        return cookiename;
    }

    public void setCookiename(String cookiename) {
        this.cookiename = cookiename;
    }

    public String getCookievalue() {
        return cookievalue;
    }

    public void setCookievalue(String cookievalue) {
        this.cookievalue = cookievalue;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Site{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", url='" + url + '\'' +
            ", selector='" + selector + '\'' +
            ", cookiename='" + cookiename + '\'' +
            ", cookievalue='" + cookievalue + '\'' +
            ", useragent='" + useragent + '\'' +
            ", timeout=" + timeout +
            ", headers=" + headers +
            ", referrer='" + referrer + '\'' +
            ", enabled=" + enabled +
            '}';
    }
}
