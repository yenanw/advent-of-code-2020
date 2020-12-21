import java.util.ArrayList;
import java.util.List;

public class MsgParser {
    private Rule[] rules;
    private String[] messages;

    public MsgParser(Rule[] rules, String[] messages) {
        this.rules = rules;
        this.messages = messages;
    }

    public boolean matches(int ruleId, String msg) {
        return getRule(ruleId).matches(msg);
    }

    public String[] allMatches(int ruleId) {
        Rule rule = getRule(ruleId);
        List<String> match = new ArrayList<>();

        for (String msg : messages) {
            if (rule.matches(msg))
                match.add(msg);
        }
        return match.toArray(String[]::new);
    }

    public Rule getRule(int ruleId) {
        for (Rule rule : rules) {
            if (rule.getID() == ruleId)
                return rule;
        }
        return null;
    }
}
