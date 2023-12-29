package com.travel.management.entities;

import com.travel.management.domain.Constants;

public enum MembershipType {
    STANDARD {
        public int getFinalCost(final int cost){
            return cost;
        }
    },
    GOLD {
        public int getFinalCost(final int cost) {
            return (int) (cost * (100 - Constants.GOLD_MEMBERSHIP_DISCOUNT_PERCENT) / 100.0);
        }
    },
    PREMIUM{
        public int getFinalCost(final int cost){
            return 0;
        }
    };

    public abstract int getFinalCost(final int cost);
}
