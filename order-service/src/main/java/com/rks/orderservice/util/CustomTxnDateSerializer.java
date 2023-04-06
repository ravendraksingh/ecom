package com.rks.orderservice.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomTxnDateSerializer extends StdSerializer<Date> {
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    public CustomTxnDateSerializer() {
        this(null);
    }
    public CustomTxnDateSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(value));
    }
}
