package com.ashwin.evolve.programs;

import java.util.ArrayList;
import java.util.List;

import com.ashwin.evolve.programs.exceptions.ExecutionException;

public class Memory {

	private String _name;
	private List<Integer> _cells;
	
	/**
	 * Fills a block of memory of the specified size and initializes all memory
	 * cells to the specified value.
	 * 
	 * @param size
	 */
	public Memory(String name, int size, Integer value) {
		_name = name;
		_cells = new ArrayList<Integer>(); 
		for(int i = 0; i < size; i++)
			_cells.add(value);
	}
	
	/**
	 * Fills a block of memory with the specified values.
	 * 
	 * @param values
	 */
	public Memory(String name, int[] values) {
		_name = name;
		_cells = new ArrayList<Integer>();
		for(int value : values)
			_cells.add(value);
	}
	
	public int size() {
		return _cells.size();
	}
	
	public int read(int address) throws ExecutionException {
		if(address < 0 || address >= _cells.size())
			throw new ExecutionException("Memory address out of range.");
		
		return _cells.get(address);
	}
	
	public void write(int address, int value) throws ExecutionException {
		if(address < 0 || address >= _cells.size())
			throw new ExecutionException("Memory address out of range.");
		
		_cells.set(address, value);
	}
	
	public void write(int start, int[] values) throws ExecutionException {
		if(start < 0 || start + values.length - 1 >= _cells.size())
			throw new ExecutionException("Memory address out of range.");
		
		for(int i = start; i < values.length; i++)
			_cells.set(i, values[i]);
	}
	
	@Override
	public String toString() {
		return _name;
	}
}