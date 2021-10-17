package com.hamrasta.trellis.ui.web.helper;

import com.hamrasta.trellis.ui.web.payload.ClientInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IWebHelper {
    default HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession();
    }

    default String getIp() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getHeader("X-FORWARDED-FOR");
        ip =  StringUtils.isBlank(ip) ? request.getRemoteAddr() : ip;
        String[] ips = StringUtils.split(ip, ",");
        return ObjectUtils.isNotEmpty(ips) ? ips[0].trim() : ip;
    }

    default ClientInfo getClientInfo() {
        return new ClientInfo(this.getIp(), this.getSession() == null ? null : this.getSession().getId());
    }
}

