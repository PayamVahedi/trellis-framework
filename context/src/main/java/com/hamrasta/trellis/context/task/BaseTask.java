package com.hamrasta.trellis.context.task;

import com.hamrasta.trellis.context.task.interfaces.IBaseTask;
import com.hamrasta.trellis.util.mapper.ModelMapper;

public abstract class BaseTask<TOutput> implements IBaseTask<TOutput>, ModelMapper {

}
