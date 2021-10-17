package com.hamrasta.trellis.ui.web.payload;

import com.hamrasta.trellis.core.payload.ResponseMessage;

public class ClientInfo extends ResponseMessage {
    private String ip;
    private String session;

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSession() {
        return this.session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public ClientInfo() {
    }

    public ClientInfo(String ip, String session) {
        this.ip = ip;
        this.session = session;
    }

    public String toString() {
        return "ClientInfo{ip='" + this.ip + '\'' + ", session='" + this.session + '\'' + '}';
    }
}
