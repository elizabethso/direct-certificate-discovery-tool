package gov.hhs.onc.dcdt.config.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gov.hhs.onc.dcdt.beans.impl.AbstractToolNamedBean;
import gov.hhs.onc.dcdt.config.InstanceMailAddressConfig;

@JsonTypeName("instanceMailAddrConfig")
public class InstanceMailAddressConfigImpl extends AbstractToolNamedBean implements InstanceMailAddressConfig {
    private String mailAddr;

    @Override
    public String getMailAddress() {
        return this.mailAddr;
    }

    @Override
    public void setMailAddress(String mailAddr) {
        this.mailAddr = mailAddr;
    }
}
