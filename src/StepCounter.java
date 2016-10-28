public class StepCounter {
	public static void main(String[] args) {
		String[] columnNames={"time", "gyro-x", "gyro-y", "gyro-z"};
		CSVData test = new CSVData("data/64StepsInPocketJogging-out.csv", columnNames, 1);
		test.correctTime(test);
		System.out.println(countSteps(test.getColumn(0),test.getRows(1,test.getNumRows()-1)));
	}
	private static int countSteps(double[] times, double[][] sensorData) {
		int stepCount = 0;
		double[] arr = new double[times.length];
		arr = calculateMagnitudes(sensorData);
		double mean = calculateMean(arr);
		double deviation = calculateStandardDeviation(arr, mean);
		System.out.println(mean+deviation);
		for(int i = 1; i < arr.length-1; i++) {
			if (arr[i] > arr[i-1] && arr[i] > arr[i+1]) {
				if(arr[i] > mean+deviation*0.8) {
					stepCount++;
					System.out.println(stepCount +" " + times[i]/1000);
				}
			}
		}
		
		return stepCount;
	}
	public static double calculateMagnitude(double x, double y, double z) {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	private static double[] calculateMagnitudes(double[][] sensorData) {
		double[] result = new double[sensorData.length];
		for(int i = 0; i < sensorData.length; i++) {
			double x = sensorData[i][1];
			double y = sensorData[i][2];
			double z = sensorData[i][3];
			result[i] = calculateMagnitude(x,y,z);
		}
		return result;
	}
	
	private static double calculateStandardDeviation(double[] arr, double mean) {
		double sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += (arr[i] - mean)*(arr[i] - mean);
		}
		return Math.sqrt(sum/(arr.length-1));
	}
	
	private static double calculateMean(double[] arr) {
		double sum = 0;
		for(int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum/arr.length;
	}
}
	