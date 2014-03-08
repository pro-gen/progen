package app.regression;

import java.util.ArrayList;
import java.util.List;

import progen.kernel.population.Individual;
import progen.userprogram.UserProgram;


public class Regression extends UserProgram {
	
	List<FitnessCase> fitnessCases = new ArrayList<FitnessCase>();
	List<FitnessCase> testFitnessCases = new ArrayList<FitnessCase>();


	private class FitnessCase {
		public double x;
		public double y;
		public double z;

		public FitnessCase (double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getZ() {
			return z;
		}
	}

	
	@Override
	public double fitness(Individual individual) {
		individual.setVariable("dX", 1.0);
		individual.setVariable("dY", 2.0);
		
		//se puede hacer el casting, porque ese arbol devuelve algo de tipo Double
		double resultado=(Double)individual.evaluate(this);
		
		final double MAX_ERROR = 10000;
		Double fitness		= 0.0;
		double error		= 0.0;
		double partialError = 0.0;
		double x			= 0.0;
		double y			= 0.0;
		double z			= 0.0;
		double result		= 0.0;

		for (int i = 0; i < fitnessCases.size(); i++){
			
			FitnessCase f = fitnessCases.get(i);
			individual.setVariable("dX", f.getX());
			individual.setVariable("dY", f.getY());
			z = f.getZ();
			result = ((Double)individual.evaluate(this)).doubleValue();
			partialError = Math.pow( z-result, 2 );

			error += partialError;

			//System.out.println("y = " + y + "; result = " + result + "; x = " + i + "; ");
		}
		fitness = Math.sqrt(error / fitnessCases.size());
		
		if (fitness.isNaN()) {
			return Double.MAX_VALUE;
		} else if (fitness.isInfinite()){
			return Double.MAX_VALUE;
		} else {
			return fitness;
		}
	}
	
	
	@Override
	public void initialize(){
		double x = 0.0;
		double y = 0.0;
		Double z = 0.0;
		int i = 0;
		while (i < 20) {
			x = Math.random() * 3;
			if (Math.random() < 0.5) {
				x *= -1;
			}
			y = Math.random() * 3;
			if (Math.random() < 0.5) {
				y *= -1;
			}
			z = SR3(x, y);
			if (!z.isNaN()) {
				fitnessCases.add(new FitnessCase(x, y, z));
				i++;
			}
		}
		
		/* Now we generate the test set*/
		for (x = -3; x < 3; x = x+0.1) {
			for (y = -3; y < 3; y = y+0.1) {
				z = SR3(x, y);
				if (!z.isNaN()) {
					testFitnessCases.add(new FitnessCase(x, y, z));
				}
			}
		}
	}
	

	private double schwefelFunction(double x, double y) {
		double a = -(x * Math.sin(Math.sqrt(Math.abs(x))));
		double b = -(y * Math.sin(Math.sqrt(Math.abs(y))));
		return a+b;
	}
	
	private double SR3(double x, double y) {
		return Math.pow(x, 4) - Math.pow(x, 3) + (Math.pow(y, 2)/2) - y;
	}
}
