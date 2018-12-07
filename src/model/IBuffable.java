package model;

import java.util.ArrayList;

import buff.Buff;

public interface IBuffable {
	public abstract void addBuff(Buff b);
	public abstract ArrayList<Buff> getBuffs();
	public abstract boolean hasBuff(int id);
	public abstract void updateBuff();
}
