package imran.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode
public class Vote implements Serializable {

    private Voter voter;
    private Candidate candidate;
}
