package com.test.happiParser;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v22.datatype.PN;
import ca.uhn.hl7v2.model.v22.message.ADT_A01;
import ca.uhn.hl7v2.model.v22.segment.MSH;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.util.Terser;
import ca.uhn.hl7v2.validation.impl.NoValidation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by ankit07 on 9/20/16.
 */
public class Main {
    public static void main(String[] args){
    String msg = "MSH|^~\\&|HIS|RIH|EKG|EKG|199904140038||ADT^A01||P|2.2\r"
            + "PID|0001|00009874|00001122|A00977|SMITH^JOHN^M|MOM|19581119|F|NOTREAL^LINDA^M|C|564 SPRING ST^^NEEDHAM^MA^02494^US|0002|(818)565-1551|(425)828-3344|E|S|C|0000444444|252-00-4414||||SA|||SA||||NONE|V1|0001|I|D.ER^50A^M110^01|ER|P00055|11B^M011^02|070615^BATMAN^GEORGE^L|555888^NOTREAL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^NOTREAL^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|199904101200||||5555112333|||666097^NOTREAL^MANNY^P\r"
            + "NK1|0222555|NOTREAL^JAMES^R|FA|STREET^OTHER STREET^CITY^ST^55566|(222)111-3333|(888)999-0000|||||||ORGANIZATION\r"
            + "PV1|0001|I|D.ER^1F^M950^01|ER|P000998|11B^M011^02|070615^BATMAN^GEORGE^L|555888^OKNEL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^VOICE^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|||||5555112333|||666097^DNOTREAL^MANNY^P\r"
            + "PV2|||0112^TESTING|55555^PATIENT IS NORMAL|NONE|||19990225|19990226|1|1|TESTING|555888^NOTREAL^BOB^K^DR^MD||||||||||PROD^003^099|02|ER||NONE|19990225|19990223|19990316|NONE\r"
            + "AL1||SEV|001^POLLEN\r"
            + "GT1||0222PL|NOTREAL^BOB^B||STREET^OTHER STREET^CITY^ST^77787|(444)999-3333|(222)777-5555||||MO|111-33-5555||||NOTREAL GILL N|STREET^OTHER STREET^CITY^ST^99999|(111)222-3333\r"
            + "IN1||022254P|4558PD|BLUE CROSS|STREET^OTHER STREET^CITY^ST^00990||(333)333-6666||221K|LENIX|||19980515|19990515|||PATIENT01 TEST D||||||||||||||||||02LL|022LP554";


    String msg2 = "MSH|^~\\&|ADT|CHMC|ProAccess||20230822181701||ADT^A08|MT142756636134091|P|2.3|||AL|NE\r" +
            "PID|1|CEUL1984055|100003^^^&2.16.840.1.113883.3.1009&ISO~011806^^^San Luis Valley Reg Med Center&2.16.840.1.113883.3.930&ISO~300713^^^San Luis Valley Reg Med Center&2.16.840.1.113883.3.930&ISO~CL0001115542^^^CO Laboratory Services CL&&ISO~5354437^^^University of Colorado Health&2.16.840.1.113883.3.2889&ISO|RM680655|BRONK^MARIANNA^|^^|19230324|F||2131-1|6999 UNIVERSITY PARK^^FEDERAL BOXES^CA^81125^^^||(408)-278-5231||EN|UNK|VAR|515-55-4543|515-55-4543||||||||||\r" +
            "PD1||||PCPUN^Pcp^Unknown\r" +
            "NK1|1|POLASKI^BOBBY^|CHD|^^^^^^^|(408)-534-4829|\r" +
            "NK1|2|TYRIE^BLAIR^|CHD|^^^^^^^|(408)-752-1465|\r" +
            "PV1|1|I|MC3WEST1^MC3706^1|C|||BUKOAL^Bukowski^Allison^Marie|BUKOAL^Bukowski^Allison^Marie|GIRAST^Girard^Stephen^Michael|HOSP||||1|||BUKOAL^Bukowski^Allison^Marie|IN||MCR|||||||||||||||||||MC|||||202308211705\r" +
            "GT1|1||BEATO^RAYMOND^||1826 LAUPPE LANE^^CAMERON^NM^81125^^^|(408)-283-1928|||||SLF|828-46-4375||||INFORMATION UNAVAILABLE\r" +
            "PV2||REG|^SBO\r" +
            "AL1|1|MA|0030^.OSA^CODED|||20160822\r" +
            "AL1|2|DA|F001000476^Penicillins^CODED|||20160822\r" +
            "AL1|3|DA|F006000658^lisinopril^CODED|||20160822\r" +
            "AL1|4|DA|F006001550^codeine^CODED|||20160822\r" +
            "GT1|2||IFFLAND^JAMIE^||7267 FOREST HILLS DR^^SAN JOSE^CA^81125^^^|(408)-869-3802|||||SLF|390-23-9821||||INFORMATION UNAVAILABLE\r" +
            "IN1|1||MCR|MEDICARE PART A AND B|PO BOX 3113^^MECHANICSBURG^PA^17055-1828||(408)-913-3814|588-22-7099|NA||INFORMATION UNAVAILABLE|||||DEMERIS^IRVING^|SLF|19230324|6632 DONEVA AVE^^LACROSSE^WA^81125^^^|||||||||||||||||523748060A|||||||F||||||523748060A\r" +
            "IN2|1|515-55-4543|||||||||||||||||||||||||||||||||||||||||U||||||||||||||||||||(408)-278-5231\r" +
            "IN1|2||BCP|BC PPO COLORADO|PO BOX 5747^^DENVER^CO^80217-5747||(408)-905-0350|311-16-3529|MEDICARE SUPPLEMENT||INFORMATION UNAVAILABLE|||||MARYOTT^JESSE^|SLF|19230324|4564 WEST RIVER DR^^HONOLULU^HI^81125^^^|||||||||||||||||PQC109A22034|||||||F||||||PQC109A22034\r" +
            "IN2|2|515-55-4543|||||||||||||||||||||||||||||||||||||||||U||||||||||||||||||||(408)-278-5231";

        Main mainClass = new Main();

        JsonObject config = mainClass.getConfig();

    HapiContext context = new DefaultHapiContext();
    context.setValidationContext(new NoValidation());
    Parser p = context.getGenericParser();
    Message hapiMsg;
    try {
        hapiMsg = p.parse(msg2);
        Terser terser = new Terser(hapiMsg);

        JsonObject rowObject = new JsonObject();

        JsonObject configBody = config.getAsJsonObject("ADT^A08");
        Set<Map.Entry<String, JsonElement>> entrySet = configBody.entrySet();
        for(Map.Entry<String,JsonElement> entry : entrySet){

            rowObject.addProperty(entry.getKey(),terser.get(entry.getValue().getAsString()));
        }

    } catch (EncodingNotSupportedException e) {
        e.printStackTrace();
        return;
    } catch (HL7Exception ex) {
        ex.printStackTrace();
        return;
    }

}

    private JsonObject getConfig() {

        JsonParser parser = new JsonParser();

        try {
            Object obj = parser.parse(new java.io.FileReader("path to your config"));
            JsonObject jsonObject = (JsonObject) obj;
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception::" + e.getMessage());

        }

        return null;
    }

}