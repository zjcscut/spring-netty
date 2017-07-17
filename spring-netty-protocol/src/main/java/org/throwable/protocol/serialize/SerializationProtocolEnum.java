package org.throwable.protocol.serialize;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:42
 */
public enum SerializationProtocolEnum {

	JDK("jdk"), KRYO("kryo"), HESSIAN("hessian"), PROTOSTUFF("protostuff");

	private String serializationProtocol;

	SerializationProtocolEnum(String serializationProtocol) {
		this.serializationProtocol = serializationProtocol;
	}

	public String getSerializationProtocol() {
		return serializationProtocol;
	}

	public static SerializationProtocolEnum forEnum(String value) {
		for (SerializationProtocolEnum each : SerializationProtocolEnum.values()) {
			if (each.serializationProtocol.equals(value)) {
				return each;
			}
		}
		throw new IllegalArgumentException("Could not found any supported serialization protocol for value:" + value);
	}
}
