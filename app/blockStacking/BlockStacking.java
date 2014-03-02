package app.blockStacking;

import progen.kernel.population.Individual;
import progen.userprogram.UserProgram;

/**
 * Block stacking problem
 * @author Alberto Vegas Estrada
 * @version 2.0
 * @since 1.0
 */
public class BlockStacking extends UserProgram{
	
	private char[][] _originalStack = { {'-','-','-','-','U','R','S','A','L'},
										{'A','U','R','S','L','I','V','N','E'},
										{'-','-','U','I','V','A','S','L','E'},
										{'-','-','-','-','-','-','-','L','U'},
										{'-','-','-','U','L','R','A','E','I'},
										{'-','-','E','V','U','N','L','A','I'},
										{'-','-','-','-','R','S','A','L','U'},
										{'L','A','S','R','E','V','I','N','U'},
										{'-','-','U','R','L','I','S','L','A'},
										{'-','-','-','R','U','N','L','A','E'},
									  };
	private char[][] _originalTable = { {'-','-','V','-','I','-','E','N','-'},
										{'-','-','-','-','-','-','-','-','-'},
										{'-','-','R','-','N','-','-','-','-'},
										{'N','V','I','E','R','S','-','A','-'},
										{'S','-','-','V','N','-','-','-','-'},
										{'-','-','R','-','S','-','-','-','-'},
										{'-','-','I','N','-','-','E','V','-'},
										{'-','-','-','-','-','-','-','-','-'},
										{'-','V','N','-','-','-','-','-','-'},
										{'-','S','-','V','-','I','-','-','-'},
		  							  };
	char[][] _stack;
	char[][] _table;
	char[] _solution = {'U','N','I','V','E','R','S','A','L'};
	int _sizeProblem;
	int _fitnessCases;
	int _maxiterdu=100;
	int _currentCase;
	
	//REMEMBER: THE LOWER FITNESS THE BETTER INDIVIDUAL
	
	@Override
	public double fitness(Individual ind){
		//Initialize the stack and table
		for(int i = 0; i<_fitnessCases; i++){
			_currentCase=i;
			_maxiterdu=100;
			for (int j = 0; j < _sizeProblem; j++){
				_stack[i][j] = _originalStack[i][j];
				_table[i][j] = _originalTable[i][j];
			}
			setSensors(i, ind);
			//Evaluate the tree
			ind.evaluate(this);
		}
		//Count the number of stack blocks in his correct place
		int hits=0;
		for(int j=0;j<_fitnessCases;j++){
			for(int i=0;i<_sizeProblem;i++){
				if(_stack[j][i]==_solution[i]){
					hits++;
				}
			}
		}
		int fitness = (_sizeProblem*_fitnessCases) - hits;
		return (double)fitness;
	}
	
	public void initialize(){
		System.out.println("BLOCKSTACKING: Loading Block Stacking initial state from file BlockStacking.trl");
		_sizeProblem = 9;
		_fitnessCases=5;
		_stack = new char[_fitnessCases][_sizeProblem];
		_table = new char[_fitnessCases][_sizeProblem];
	}
	
	public void setSensors(int caseFitness, Individual individual){
		boolean set=false;
		for(int i = 0; i<_sizeProblem && !set; i++){
			if(_stack[caseFitness][i]!='-'){
				individual.setVariable("CS", (Character)_stack[caseFitness][i]);
				set=true;
			}
		}
		set=false;
		for(int i = _sizeProblem-1; i>=0 && !set; i--){
			if(_stack[caseFitness][i]!=_solution[i]){
				if(i==_sizeProblem-1){
					individual.setVariable("TB", (Character)null);
					individual.setVariable("NN", (Character)_solution[i]);
					set=true;
				}else{
					individual.setVariable("TB", (Character)_stack[caseFitness][i+1]);
					individual.setVariable("NN", (Character)_solution[i]);
					set=true;
				}
			}
		}
	}
	
	public void printInitialState(int fitnessCase){
		System.out.println("Initial State Case "+fitnessCase+":\n");
		for (int i = 0; i < _sizeProblem; i++)
		{
			System.out.println(_originalStack[fitnessCase][i]);
		}
		System.out.print("\t");
		for (int i = 0; i < _sizeProblem; i++)
		{
			System.out.print(_originalTable[fitnessCase][i]+" ");
		}
		System.out.println();
	}
	
	public void printCurrentState(int fitnessCase){
		System.out.println("Current State Case "+fitnessCase+"\n");
		for (int i = 0; i < _sizeProblem; i++)
		{
			System.out.println(_stack[fitnessCase][i]);
		}
		System.out.print("\t");
		for (int i = 0; i < _sizeProblem; i++)
		{
			System.out.print(_table[fitnessCase][i]+" ");
		}
		System.out.println();
	}
}
