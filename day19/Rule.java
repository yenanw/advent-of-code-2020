import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rule {
    private List<Rule> fst = null;
    private List<Rule> snd = null;
    private String match = null;

    private int id;
    private RuleType ruleType;

    private enum RuleType {
        MATCH, CASCADE, CASCADE_OR
    }

    public Rule(int id, String match) {
        this.id = id;
        this.match = match;
        this.ruleType = RuleType.MATCH;
    }

    public Rule(int id, List<Rule> fstRules) {
        this.id = id;
        this.fst = fstRules;
        this.ruleType = RuleType.CASCADE;
    }

    public Rule(int id, List<Rule> fstRules, List<Rule> sndRules) {
        this(id, fstRules);
        this.snd = sndRules;
        this.ruleType = RuleType.CASCADE_OR;
    }

    public int getID() {
        return id;
    }

    public List<Rule> getFirstRules() {
        return fst;
    }

    public List<Rule> getSecondRules() {
        return snd;
    }

    public boolean matches(String msg) {
        for (int last : matches(msg, 0)) {
            String parsed = msg.substring(0, last);
            
            if (msg.equals(parsed))
                return true;
        } 
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(":");
        switch (ruleType) {
            case MATCH:
                sb.append(" \"").append(match).append("\"");
                break;
            case CASCADE:
                for (Rule r : fst) {
                    sb.append(" ").append(r.getID());
                }
                break;
            case CASCADE_OR:
                for (Rule r : fst) {
                    sb.append(" ").append(r.getID());
                }
                sb.append(" | ");
                for (Rule r : snd) {
                    sb.append(r.getID()).append(" ");
                }
                break;
        }
        return sb.toString();
    }

    private Set<Integer> matches(String msg, int start) {
        return switch (ruleType) {
            case MATCH -> isMatch(msg, start);
            case CASCADE -> passMatchTo(fst, msg, start);
            case CASCADE_OR -> passMatchToBoth(msg, start);
            default -> new HashSet<>();
        };
    }

    private Set<Integer> isMatch(String msg, int start) {
        if (start >= msg.length())
            return new HashSet<>();

        int n = start + match.length();
        Set<Integer> next = new HashSet<>();
        next.add(n);

        String m = msg.substring(start, n);
        if (match.equals(m))
            return next;

        return new HashSet<>();
    }

    private Set<Integer> passMatchTo(List<Rule> rules, String msg, int start) {
        Set<Integer> next = new HashSet<>();
        next.addAll(rules.get(0).matches(msg, start)); 
        for (int i = 1; i < rules.size(); i++) {
            if (next.isEmpty())
                return new HashSet<>();
            else {
                Set<Integer> nNext = new HashSet<>();
                for (int n : next)
                   nNext.addAll(rules.get(i).matches(msg, n));
                next = nNext;
            }
        }
        return next;
    }

    private Set<Integer> passMatchToBoth(String msg, int start) {
        Set<Integer> r1 = passMatchTo(fst, msg, start);
        Set<Integer> r2 = passMatchTo(snd, msg, start);
        r1.addAll(r2);
        return r1;
    }
}
