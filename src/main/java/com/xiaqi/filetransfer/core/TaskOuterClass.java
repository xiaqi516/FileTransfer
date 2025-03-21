// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: task.proto
// Protobuf Java Version: 4.30.0

package com.xiaqi.filetransfer.core;

public final class TaskOuterClass {
  private TaskOuterClass() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 30,
      /* patch= */ 0,
      /* suffix= */ "",
      TaskOuterClass.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface TaskOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.xiaqi.filetransfer.core.Task)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string hash = 1;</code>
     * @return The hash.
     */
    String getHash();
    /**
     * <code>string hash = 1;</code>
     * @return The bytes for hash.
     */
    com.google.protobuf.ByteString
        getHashBytes();

    /**
     * <code>string fileName = 2;</code>
     * @return The fileName.
     */
    String getFileName();
    /**
     * <code>string fileName = 2;</code>
     * @return The bytes for fileName.
     */
    com.google.protobuf.ByteString
        getFileNameBytes();

    /**
     * <code>string filePath = 3;</code>
     * @return The filePath.
     */
    String getFilePath();
    /**
     * <code>string filePath = 3;</code>
     * @return The bytes for filePath.
     */
    com.google.protobuf.ByteString
        getFilePathBytes();

    /**
     * <code>uint64 size = 4;</code>
     * @return The size.
     */
    long getSize();

    /**
     * <code>string address = 5;</code>
     * @return The address.
     */
    String getAddress();
    /**
     * <code>string address = 5;</code>
     * @return The bytes for address.
     */
    com.google.protobuf.ByteString
        getAddressBytes();

    /**
     * <code>string direction = 6;</code>
     * @return The direction.
     */
    String getDirection();
    /**
     * <code>string direction = 6;</code>
     * @return The bytes for direction.
     */
    com.google.protobuf.ByteString
        getDirectionBytes();
  }
  /**
   * Protobuf type {@code com.xiaqi.filetransfer.core.Task}
   */
  public static final class Task extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:com.xiaqi.filetransfer.core.Task)
      TaskOrBuilder {
  private static final long serialVersionUID = 0L;
    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 30,
        /* patch= */ 0,
        /* suffix= */ "",
        Task.class.getName());
    }
    // Use Task.newBuilder() to construct.
    private Task(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
    }
    private Task() {
      hash_ = "";
      fileName_ = "";
      filePath_ = "";
      address_ = "";
      direction_ = "";
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return TaskOuterClass.internal_static_com_xiaqi_filetransfer_core_Task_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return TaskOuterClass.internal_static_com_xiaqi_filetransfer_core_Task_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Task.class, Builder.class);
    }

    public static final int HASH_FIELD_NUMBER = 1;
    @SuppressWarnings("serial")
    private volatile Object hash_ = "";
    /**
     * <code>string hash = 1;</code>
     * @return The hash.
     */
    @Override
    public String getHash() {
      Object ref = hash_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        hash_ = s;
        return s;
      }
    }
    /**
     * <code>string hash = 1;</code>
     * @return The bytes for hash.
     */
    @Override
    public com.google.protobuf.ByteString
        getHashBytes() {
      Object ref = hash_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        hash_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int FILENAME_FIELD_NUMBER = 2;
    @SuppressWarnings("serial")
    private volatile Object fileName_ = "";
    /**
     * <code>string fileName = 2;</code>
     * @return The fileName.
     */
    @Override
    public String getFileName() {
      Object ref = fileName_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        fileName_ = s;
        return s;
      }
    }
    /**
     * <code>string fileName = 2;</code>
     * @return The bytes for fileName.
     */
    @Override
    public com.google.protobuf.ByteString
        getFileNameBytes() {
      Object ref = fileName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        fileName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int FILEPATH_FIELD_NUMBER = 3;
    @SuppressWarnings("serial")
    private volatile Object filePath_ = "";
    /**
     * <code>string filePath = 3;</code>
     * @return The filePath.
     */
    @Override
    public String getFilePath() {
      Object ref = filePath_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        filePath_ = s;
        return s;
      }
    }
    /**
     * <code>string filePath = 3;</code>
     * @return The bytes for filePath.
     */
    @Override
    public com.google.protobuf.ByteString
        getFilePathBytes() {
      Object ref = filePath_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        filePath_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SIZE_FIELD_NUMBER = 4;
    private long size_ = 0L;
    /**
     * <code>uint64 size = 4;</code>
     * @return The size.
     */
    @Override
    public long getSize() {
      return size_;
    }

    public static final int ADDRESS_FIELD_NUMBER = 5;
    @SuppressWarnings("serial")
    private volatile Object address_ = "";
    /**
     * <code>string address = 5;</code>
     * @return The address.
     */
    @Override
    public String getAddress() {
      Object ref = address_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        address_ = s;
        return s;
      }
    }
    /**
     * <code>string address = 5;</code>
     * @return The bytes for address.
     */
    @Override
    public com.google.protobuf.ByteString
        getAddressBytes() {
      Object ref = address_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        address_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DIRECTION_FIELD_NUMBER = 6;
    @SuppressWarnings("serial")
    private volatile Object direction_ = "";
    /**
     * <code>string direction = 6;</code>
     * @return The direction.
     */
    @Override
    public String getDirection() {
      Object ref = direction_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        direction_ = s;
        return s;
      }
    }
    /**
     * <code>string direction = 6;</code>
     * @return The bytes for direction.
     */
    @Override
    public com.google.protobuf.ByteString
        getDirectionBytes() {
      Object ref = direction_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        direction_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(hash_)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 1, hash_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(fileName_)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 2, fileName_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(filePath_)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 3, filePath_);
      }
      if (size_ != 0L) {
        output.writeUInt64(4, size_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(address_)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 5, address_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(direction_)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 6, direction_);
      }
      getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(hash_)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(1, hash_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(fileName_)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(2, fileName_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(filePath_)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(3, filePath_);
      }
      if (size_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(4, size_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(address_)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(5, address_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(direction_)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(6, direction_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Task)) {
        return super.equals(obj);
      }
      Task other = (Task) obj;

      if (!getHash()
          .equals(other.getHash())) return false;
      if (!getFileName()
          .equals(other.getFileName())) return false;
      if (!getFilePath()
          .equals(other.getFilePath())) return false;
      if (getSize()
          != other.getSize()) return false;
      if (!getAddress()
          .equals(other.getAddress())) return false;
      if (!getDirection()
          .equals(other.getDirection())) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + HASH_FIELD_NUMBER;
      hash = (53 * hash) + getHash().hashCode();
      hash = (37 * hash) + FILENAME_FIELD_NUMBER;
      hash = (53 * hash) + getFileName().hashCode();
      hash = (37 * hash) + FILEPATH_FIELD_NUMBER;
      hash = (53 * hash) + getFilePath().hashCode();
      hash = (37 * hash) + SIZE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getSize());
      hash = (37 * hash) + ADDRESS_FIELD_NUMBER;
      hash = (53 * hash) + getAddress().hashCode();
      hash = (37 * hash) + DIRECTION_FIELD_NUMBER;
      hash = (53 * hash) + getDirection().hashCode();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Task parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Task parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Task parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Task parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Task parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Task parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Task parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static Task parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Task parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static Task parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Task parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static Task parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Task prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.xiaqi.filetransfer.core.Task}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.xiaqi.filetransfer.core.Task)
        TaskOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return TaskOuterClass.internal_static_com_xiaqi_filetransfer_core_Task_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return TaskOuterClass.internal_static_com_xiaqi_filetransfer_core_Task_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Task.class, Builder.class);
      }

      // Construct using com.xiaqi.filetransfer.core.TaskOuterClass.Task.newBuilder()
      private Builder() {

      }

      private Builder(
          BuilderParent parent) {
        super(parent);

      }
      @Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        hash_ = "";
        fileName_ = "";
        filePath_ = "";
        size_ = 0L;
        address_ = "";
        direction_ = "";
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return TaskOuterClass.internal_static_com_xiaqi_filetransfer_core_Task_descriptor;
      }

      @Override
      public Task getDefaultInstanceForType() {
        return Task.getDefaultInstance();
      }

      @Override
      public Task build() {
        Task result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public Task buildPartial() {
        Task result = new Task(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(Task result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.hash_ = hash_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.fileName_ = fileName_;
        }
        if (((from_bitField0_ & 0x00000004) != 0)) {
          result.filePath_ = filePath_;
        }
        if (((from_bitField0_ & 0x00000008) != 0)) {
          result.size_ = size_;
        }
        if (((from_bitField0_ & 0x00000010) != 0)) {
          result.address_ = address_;
        }
        if (((from_bitField0_ & 0x00000020) != 0)) {
          result.direction_ = direction_;
        }
      }

      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Task) {
          return mergeFrom((Task)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Task other) {
        if (other == Task.getDefaultInstance()) return this;
        if (!other.getHash().isEmpty()) {
          hash_ = other.hash_;
          bitField0_ |= 0x00000001;
          onChanged();
        }
        if (!other.getFileName().isEmpty()) {
          fileName_ = other.fileName_;
          bitField0_ |= 0x00000002;
          onChanged();
        }
        if (!other.getFilePath().isEmpty()) {
          filePath_ = other.filePath_;
          bitField0_ |= 0x00000004;
          onChanged();
        }
        if (other.getSize() != 0L) {
          setSize(other.getSize());
        }
        if (!other.getAddress().isEmpty()) {
          address_ = other.address_;
          bitField0_ |= 0x00000010;
          onChanged();
        }
        if (!other.getDirection().isEmpty()) {
          direction_ = other.direction_;
          bitField0_ |= 0x00000020;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 10: {
                hash_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
              case 18: {
                fileName_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000002;
                break;
              } // case 18
              case 26: {
                filePath_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000004;
                break;
              } // case 26
              case 32: {
                size_ = input.readUInt64();
                bitField0_ |= 0x00000008;
                break;
              } // case 32
              case 42: {
                address_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000010;
                break;
              } // case 42
              case 50: {
                direction_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000020;
                break;
              } // case 50
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private Object hash_ = "";
      /**
       * <code>string hash = 1;</code>
       * @return The hash.
       */
      public String getHash() {
        Object ref = hash_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          hash_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string hash = 1;</code>
       * @return The bytes for hash.
       */
      public com.google.protobuf.ByteString
          getHashBytes() {
        Object ref = hash_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          hash_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string hash = 1;</code>
       * @param value The hash to set.
       * @return This builder for chaining.
       */
      public Builder setHash(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        hash_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>string hash = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearHash() {
        hash_ = getDefaultInstance().getHash();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>string hash = 1;</code>
       * @param value The bytes for hash to set.
       * @return This builder for chaining.
       */
      public Builder setHashBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        hash_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }

      private Object fileName_ = "";
      /**
       * <code>string fileName = 2;</code>
       * @return The fileName.
       */
      public String getFileName() {
        Object ref = fileName_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          fileName_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string fileName = 2;</code>
       * @return The bytes for fileName.
       */
      public com.google.protobuf.ByteString
          getFileNameBytes() {
        Object ref = fileName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          fileName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string fileName = 2;</code>
       * @param value The fileName to set.
       * @return This builder for chaining.
       */
      public Builder setFileName(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        fileName_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>string fileName = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearFileName() {
        fileName_ = getDefaultInstance().getFileName();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
        return this;
      }
      /**
       * <code>string fileName = 2;</code>
       * @param value The bytes for fileName to set.
       * @return This builder for chaining.
       */
      public Builder setFileNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        fileName_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }

      private Object filePath_ = "";
      /**
       * <code>string filePath = 3;</code>
       * @return The filePath.
       */
      public String getFilePath() {
        Object ref = filePath_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          filePath_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string filePath = 3;</code>
       * @return The bytes for filePath.
       */
      public com.google.protobuf.ByteString
          getFilePathBytes() {
        Object ref = filePath_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          filePath_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string filePath = 3;</code>
       * @param value The filePath to set.
       * @return This builder for chaining.
       */
      public Builder setFilePath(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        filePath_ = value;
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }
      /**
       * <code>string filePath = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearFilePath() {
        filePath_ = getDefaultInstance().getFilePath();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
        return this;
      }
      /**
       * <code>string filePath = 3;</code>
       * @param value The bytes for filePath to set.
       * @return This builder for chaining.
       */
      public Builder setFilePathBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        filePath_ = value;
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }

      private long size_ ;
      /**
       * <code>uint64 size = 4;</code>
       * @return The size.
       */
      @Override
      public long getSize() {
        return size_;
      }
      /**
       * <code>uint64 size = 4;</code>
       * @param value The size to set.
       * @return This builder for chaining.
       */
      public Builder setSize(long value) {

        size_ = value;
        bitField0_ |= 0x00000008;
        onChanged();
        return this;
      }
      /**
       * <code>uint64 size = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearSize() {
        bitField0_ = (bitField0_ & ~0x00000008);
        size_ = 0L;
        onChanged();
        return this;
      }

      private Object address_ = "";
      /**
       * <code>string address = 5;</code>
       * @return The address.
       */
      public String getAddress() {
        Object ref = address_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          address_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string address = 5;</code>
       * @return The bytes for address.
       */
      public com.google.protobuf.ByteString
          getAddressBytes() {
        Object ref = address_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          address_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string address = 5;</code>
       * @param value The address to set.
       * @return This builder for chaining.
       */
      public Builder setAddress(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        address_ = value;
        bitField0_ |= 0x00000010;
        onChanged();
        return this;
      }
      /**
       * <code>string address = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearAddress() {
        address_ = getDefaultInstance().getAddress();
        bitField0_ = (bitField0_ & ~0x00000010);
        onChanged();
        return this;
      }
      /**
       * <code>string address = 5;</code>
       * @param value The bytes for address to set.
       * @return This builder for chaining.
       */
      public Builder setAddressBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        address_ = value;
        bitField0_ |= 0x00000010;
        onChanged();
        return this;
      }

      private Object direction_ = "";
      /**
       * <code>string direction = 6;</code>
       * @return The direction.
       */
      public String getDirection() {
        Object ref = direction_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          direction_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string direction = 6;</code>
       * @return The bytes for direction.
       */
      public com.google.protobuf.ByteString
          getDirectionBytes() {
        Object ref = direction_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          direction_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string direction = 6;</code>
       * @param value The direction to set.
       * @return This builder for chaining.
       */
      public Builder setDirection(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        direction_ = value;
        bitField0_ |= 0x00000020;
        onChanged();
        return this;
      }
      /**
       * <code>string direction = 6;</code>
       * @return This builder for chaining.
       */
      public Builder clearDirection() {
        direction_ = getDefaultInstance().getDirection();
        bitField0_ = (bitField0_ & ~0x00000020);
        onChanged();
        return this;
      }
      /**
       * <code>string direction = 6;</code>
       * @param value The bytes for direction to set.
       * @return This builder for chaining.
       */
      public Builder setDirectionBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        direction_ = value;
        bitField0_ |= 0x00000020;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.xiaqi.filetransfer.core.Task)
    }

    // @@protoc_insertion_point(class_scope:com.xiaqi.filetransfer.core.Task)
    private static final Task DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Task();
    }

    public static Task getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Task>
        PARSER = new com.google.protobuf.AbstractParser<Task>() {
      @Override
      public Task parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<Task> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Task> getParserForType() {
      return PARSER;
    }

    @Override
    public Task getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_xiaqi_filetransfer_core_Task_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_xiaqi_filetransfer_core_Task_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\ntask.proto\022\033com.xiaqi.filetransfer.cor" +
      "e\"j\n\004Task\022\014\n\004hash\030\001 \001(\t\022\020\n\010fileName\030\002 \001(" +
      "\t\022\020\n\010filePath\030\003 \001(\t\022\014\n\004size\030\004 \001(\004\022\017\n\007add" +
      "ress\030\005 \001(\t\022\021\n\tdirection\030\006 \001(\tb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_xiaqi_filetransfer_core_Task_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_xiaqi_filetransfer_core_Task_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_com_xiaqi_filetransfer_core_Task_descriptor,
        new String[] { "Hash", "FileName", "FilePath", "Size", "Address", "Direction", });
    descriptor.resolveAllFeaturesImmutable();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
