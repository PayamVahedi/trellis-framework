package com.hamrasta.trellis.communication.proto.server.service;

import com.hamrasta.trellis.communication.proto.server.provider.ProtoActionContextProvider;
import com.hamrasta.trellis.context.provider.ActionContextProvider;
import com.hamrasta.trellis.util.mapper.ModelMapper;

public interface IProto extends ModelMapper, ProtoActionContextProvider, ActionContextProvider {

}
