package com.hamrasta.trellis.context.task.interfaces;

import com.hamrasta.trellis.context.process.IProcess;

public interface ITask<TOutput> extends IBaseTask<TOutput>, IProcess<TOutput> {

}
