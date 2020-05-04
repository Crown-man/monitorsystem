package com.cidp.monitorsystem.util.getSnmpf;

import org.snmp4j.CommunityTarget;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;

import java.io.IOException;

public class InitSnmp {
    public static CommunityTarget Init(String hostComputer,String port ,String community) throws IOException {
        String target="udp:"+hostComputer+"/"+port;
        Address targetAddress= GenericAddress.parse(target);
        CommunityTarget communityTarget;
        communityTarget = new CommunityTarget();
        communityTarget.setCommunity(new OctetString(community));
        communityTarget.setRetries(2);
        communityTarget.setAddress(targetAddress);
        communityTarget.setTimeout(8000);
        communityTarget.setVersion(SnmpConstants.version2c);
        return communityTarget;
    }

}
