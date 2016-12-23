package imran.api;

import imran.domain.Voter;

import java.io.Serializable;

public interface VoterService extends Serializable {

    Voter nextVoter();
}