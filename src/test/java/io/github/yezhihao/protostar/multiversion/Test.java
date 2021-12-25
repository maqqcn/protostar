package io.github.yezhihao.protostar.multiversion;

import io.github.yezhihao.protostar.ProtostarUtil;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.util.ArrayMap;
import io.github.yezhihao.protostar.schema.RuntimeSchema;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.time.LocalDateTime;

public class Test {

    public static void main(String[] args) {
        ArrayMap<RuntimeSchema> multiVersionSchema = ProtostarUtil.getRuntimeSchema(Foo.class);
        RuntimeSchema<Foo> schema_v0 = multiVersionSchema.get(0);
        RuntimeSchema<Foo> schema_v1 = multiVersionSchema.get(1);

        ByteBuf buffer = Unpooled.buffer(32);
        Foo foo = foo();
        schema_v0.writeTo(buffer, foo);
        String hex = ByteBufUtil.hexDump(buffer);
        System.out.println(hex);

        foo = schema_v0.readFrom(buffer);
        System.out.println(foo);
        System.out.println("=========================version: 0");

        buffer = Unpooled.buffer(32);
        schema_v1.writeTo(buffer, foo());
        hex = ByteBufUtil.hexDump(buffer);
        System.out.println(hex);

        foo = schema_v1.readFrom(buffer);
        System.out.println(foo);
        System.out.println("=========================version: 1");
    }

    public static Foo foo() {
        Foo foo = new Foo();
        foo.setName("张三");
        foo.setId(128);
        foo.setDateTime(LocalDateTime.of(2020, 7, 7, 19, 23, 59));
        return foo;
    }

    public static class Foo {

        @Field(lengthUnit = 1, desc = "名称", version = 0)
        @Field(length = 10, desc = "名称", version = 1)
        private String name;
        @Field(length = 2, desc = "ID", version = 0)
        @Field(length = 4, desc = "ID", version = 1)
        private int id;
        @Field(charset = "BCD", desc = "日期", version = {0, 1})
        private LocalDateTime dateTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Foo{");
            sb.append("name='").append(name).append('\'');
            sb.append(", id=").append(id);
            sb.append(", dateTime=").append(dateTime);
            sb.append('}');
            return sb.toString();
        }
    }
}
