package org.jdbg.core;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jdbg.core.attach.AttachManager;
import org.jdbg.core.pipeline.impl.main.PipelineMain;
import org.jdbg.core.pipeline.response.FieldResponseData;
import org.jdbg.gui.tabs.objectanalysis.objlist.TagItem;
import org.jdbg.logger.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// the gui should interact with this only to achieve the goals
public class CoreInterface {
    static CoreInterface instance;
    public boolean attach(int pid) {
        return AttachManager.getInstance().attach(pid);
    }


    public List<String> getLoadedClassNames() {
        if(!AttachManager.getInstance().isAttached())
            return new ArrayList<>();

        PipelineMain.ClientResponse response = PipelineMain.getInstance().sendAndAwait(PipelineMain.ServerCommand.GET_LOADED_CLASS_NAMES);

        List<String> names = new ArrayList<>();

        StringBuilder s = new StringBuilder();
        for(byte b : response.response) {
            if(b=='\0') {
                if(s.length()>1 && !s.toString().startsWith("[") && !s.toString().contains("$$Lambda$"))  {

                    names.add(s.toString());
                }
                s = new StringBuilder();
                continue;
            }
            s.append((char)b);
        }
        return names;
    }

    public List<Integer> getObjectTags(String klass) {
        return getObjectTags(klass, "");
    }


    public List<Integer> getObjectTags(String klass, String filter) {
        if(!AttachManager.getInstance().isAttached())
            return new ArrayList<>();

        klass += '\0';
        filter += '\0';

        byte[] a = klass.getBytes(StandardCharsets.UTF_8);
        byte[] b = filter.getBytes(StandardCharsets.UTF_8);
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);


        PipelineMain.ClientResponse response = PipelineMain.getInstance().sendAndAwait(PipelineMain.ServerCommand.GET_OBJECT_TAGS,
                c);

        List<Integer> tags = new ArrayList<>();

        // size of long/int
        for(int i = 0; i < Math.min(response.response.length,40000); i+=4) {
            Integer l = Util.toInteger(response.response[i], response.response[i+1], response.response[i+2], response.response[i+3]);
            tags.add(l);
        }

        Logger.log("Got " + tags.size() + " objects.");

        return tags;
    }


    public FieldResponseData getFields(TagItem item) {
        String klass = item.getKlass();
        klass += '\0';


        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(item.getTag());
        byte[] bytes = byteBuffer.array();


        byte[] klassBytes = klass.getBytes(StandardCharsets.UTF_8);
        byte[] buffer = new byte[bytes.length + klassBytes.length];

        System.arraycopy(bytes, 0, buffer, 0, bytes.length);
        System.arraycopy(klassBytes, 0, buffer, 4, klassBytes.length);


        PipelineMain.ClientResponse response = PipelineMain.getInstance().sendAndAwait(PipelineMain.ServerCommand.GET_FIELDS,
                buffer);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Logger.log("response size: "+  response.response.length);
            System.out.println(new String(response.response));
            return mapper.readValue(new String(response.response), FieldResponseData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<String> getReferences(TagItem item) {
        String klass = item.getKlass();
        klass += '\0';


        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(item.getTag());
        byte[] bytes = byteBuffer.array();


        byte[] klassBytes = klass.getBytes(StandardCharsets.UTF_8);
        byte[] buffer = new byte[bytes.length + klassBytes.length];

        System.arraycopy(bytes, 0, buffer, 0, bytes.length);
        System.arraycopy(klassBytes, 0, buffer, 4, klassBytes.length);

        PipelineMain.ClientResponse response = PipelineMain.getInstance().sendAndAwait(PipelineMain.ServerCommand.GET_REFS,
                buffer);

        return new ArrayList<>();
    }









    public byte[] getClassData(String name) {
        if(!AttachManager.getInstance().isAttached())
            return new byte[0];


        name += '\0';

        PipelineMain.ClientResponse response = PipelineMain.getInstance().sendAndAwait(PipelineMain.ServerCommand.GET_CLASS_DATA, name.getBytes(StandardCharsets.UTF_8));
        Logger.log("Got class data size: " + response.response.length);

        return response.response;
    }







    public static CoreInterface getInstance() {
        if(instance == null) instance = new CoreInterface();
        return instance;
    }


}
