package com.hamrasta.trellis.context.action.intefaces;

import com.hamrasta.trellis.context.process.IProcess;

public interface IAction<TOutput> extends IBaseAction<TOutput>, IProcess<TOutput> {

}
