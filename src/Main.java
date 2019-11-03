import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<ArrayList<String>> corrects = new ArrayList<>();

        ArrayList<String> correct1 = TextLib.readDoc("data/CorrectOrders/CorrectOrder1.txt");
        ArrayList<String> correct2 = TextLib.readDoc("data/CorrectOrders/CorrectOrder1.txt");
        ArrayList<String> correct3 = TextLib.readDoc("data/CorrectOrders/CorrectOrder1.txt");
        ArrayList<String> correct4 = TextLib.readDoc("data/CorrectOrders/CorrectOrder1.txt");

        ArrayList<Question> Trials = new ArrayList<>();

        Trials.add(TextLib.readAnswersDoc("data/Questions/Question1.txt"));
        Trials.add(TextLib.readAnswersDoc("data/Questions/Question2.txt"));
        Trials.add(TextLib.readAnswersDoc("data/Questions/Question3.txt"));
        Trials.add(TextLib.readAnswersDoc("data/Questions/Question4.txt"));

        for (Question q: Trials) {
            printScores(q);

        }


        // Calculating Accucracy
        double percentage = 0;
        ArrayList<String> answers1 = reOrderbybest(Trials.get(0).getAnswers(), Score(Trials.get(0)));
        percentage += correctness(answers1, correct1);
        ArrayList<String> answers2 = reOrderbybest(Trials.get(1).getAnswers(), Score(Trials.get(2)));
        percentage += correctness(answers2, correct2);
        ArrayList<String> answers3 = reOrderbybest(Trials.get(2).getAnswers(), Score(Trials.get(3)));
        percentage += correctness(answers3, correct3);
        ArrayList<String> answers4 = reOrderbybest(Trials.get(3).getAnswers(), Score(Trials.get(3)));
        percentage += correctness(answers4, correct4);
        System.out.println(percentage);


    }

    private static void printList(ArrayList<String> list){
        for (String c: list) {
            System.out.println(c);
        }
    }

    private static void printScores(Question q) {
        System.out.println("The question is: " + q.getQuestion());
        for (int i = 0; i < q.getAnswers().size(); i++) {
            System.out.println("Answer " + (i+1) + ": " + q.getAnswers().get(i).getText());

        }
        ArrayList<Double> scores = Score(q);
        System.out.println("Their respective scores are: " + scores);
        System.out.println();
    }

    private static ArrayList<Double> Score(Question q) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < q.getAnswers().size(); i++) {
            int dq = 0;
            if(q.getAnswers().get(i).CalcWords().size() <= 5){
                dq = - 50;
            }

            double weighedRValue = (100 - q.getAnswers().get(i).getReadability());
            double weighedSentRatio = (q.getAnswers().get(i).wordsSentRatio());
            double weighedSwearWords = (q.getAnswers().get(i).countSpecificWords("data/swearWords.txt")) * 50;
            double weighedCueWordsCount = q.getAnswers().get(i).countSpecificWords("data/cueWords.txt");
            double weighedNumSameWordsInQuestSent = q.similarityWithAnswers() * 2;

            double calc = dq + weighedRValue + weighedSentRatio + weighedNumSameWordsInQuestSent - weighedSwearWords + weighedCueWordsCount;
            list.add(calc);
        }


        return list;
    }

    private static ArrayList<String> reOrderbybest(ArrayList<Answer> answers, ArrayList<Double> scores){
        ArrayList<String> reOrder = new ArrayList<>();
        Double[] score = ListtoArray(scores);
        for (int i = 0; i < score.length; i++) {
            int j = getIndexOfGreatest(score);
            reOrder.add(answers.get(j).getText());
            score[j] = -999999.0;

        }
        return reOrder;
    }

    private static Double[] ListtoArray(ArrayList<Double>scores){
        Double[] output = new Double[scores.size()];
        for (int i = 0; i < scores.size(); i++) {
            output[i] = scores.get(i);
        }
        return output;
    }



    private static int getIndexOfGreatest(Double[] scores){
        int largest = 0;
        for (int i = 0; i < scores.length; i++) {
            if(scores[largest] < scores[i]) largest = i;
        }
        return largest;
    }


    private static double correctness(ArrayList<String> answers, ArrayList<String> correct){
        int count = 0;
        for (int i = 0; i < correct.size(); i++) {
            if(correct.get(i).equals(answers.get(i))) count++;
        }
        return count;
    }

}


