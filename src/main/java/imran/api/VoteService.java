package imran.api;

import imran.domain.Vote;

import java.io.Serializable;

public interface VoteService extends Serializable {

    Vote castVote();
}
