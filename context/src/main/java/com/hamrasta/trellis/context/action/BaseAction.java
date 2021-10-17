package com.hamrasta.trellis.context.action;

import com.hamrasta.trellis.context.action.intefaces.IBaseAction;
import com.hamrasta.trellis.context.provider.ProcessContextProvider;
import com.hamrasta.trellis.util.mapper.ModelMapper;
import org.dozer.DozerBeanMapper;

public abstract class BaseAction<TOutput> implements IBaseAction<TOutput>, ModelMapper, ProcessContextProvider {

}
