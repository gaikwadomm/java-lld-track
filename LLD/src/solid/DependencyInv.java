package solid;

// Quite Inappropriate Implementation

// class TrendingRecommendation {
//     void recommend(){
//         System.out.println("Joining Rubrics!");
//     }
// }

// class GeneralRecommendation {
//     void recommend(){
//         System.out.println("Joining Google!");
//     }
// }

// class RecentRecommendation {
//     void recentRecommend(){
//         System.out.println("Joining Jane Street!");
//     }
// } 

// Using Dependency Inversion

interface Recommentdations {
    void recommend();
}

class TrendingRecommendation implements Recommentdations {
    public void recommend(){
        System.out.println("Joining Rubrics!");
    }
}

class GeneralRecommendation implements Recommentdations {
    public void recommend(){
        System.out.println("Joining Google!");
    }
}

class RecentRecommendation implements Recommentdations {
    public void recommend(){
        System.out.println("Joining Jane Street!");
    }
} 

class RecommendationSystem {
    private Recommentdations reqRecommendation;
    RecommendationSystem(Recommentdations reqRecommendation){
        this.reqRecommendation = reqRecommendation;
    }

    void recommend(){
        reqRecommendation.recommend();
    }
}

public class DependencyInv {
    public static void main(String[] args){
        // RecentRecommendation recentRec = new RecentRecommendation();

        // In this case the currently client (main) is tightly coupled
        // with the low level model 
        // any time we add some new recommendation type we may required to make much changes
        // recentRec.recentRecommend();

        Recommentdations myRecom = new RecentRecommendation();

        myRecom.recommend();
    }   
}
