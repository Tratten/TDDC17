
;; This is a small problem instance for the standard Logistics domain,
;; as defined in "logistic.pddl".

(define (problem C3_2)
  (:domain logistics)
  (:objects
   city1 city2 city3 city4              ;; Cities
   smallTruck1 smallTruck2 smallTruck3 smallTruck4      ;; smallTrucks
   mediumTruck1 mediumTruck2 mediumTruck3 mediumTruck4 ;; mediumTrucks
   bigTruck1 bigTruck2 bigTruck3 bigTruck4 ;; bigTrucks
   boat1                                ;; boats :)
   airplane1                            ;; only one airplane,
   office1 office2 office3 office4      ;; offices are "non-airport" locations
   airport1 airport2                    ;; airports, one per city,
   harbor1 harbor2 harbor3              ;;  boats gotta float somewhere :)
   packet1 packet2 packet3              ;;  small packages
   mediumPacket1 mediumPacket2          ;;  medium packages
   bigPacket1 bigPacket2                ;;  big packages
   )
  (:init
   ;; Type declarations:
   (object packet1) (object packet2) (object packet3)
  
   (object mediumPacket1) (object mediumPacket2) 
   
   (object bigPacket1)(object bigPacket2)

   (smallPacket packet1) (smallPacket packet2)
   
   (mediumPacket mediumPacket1) (mediumPacket mediumPacket2)
   
   (bigPacket bigPacket1) (bigPacket bigPacket2)

   ;; all vehicles must be declared as both "vehicle" and their
   ;; appropriate subtype,
   (vehicle bigTruck1) (vehicle bigTruck2) (vehicle bigTruck3) (vehicle bigTruck4) 
   (vehicle airplane1) (vehicle boat1)

   ;; All trucks must be declared truck.

   ;; small trucks
   (truck smallTruck1) (truck smallTruck2) (truck smallTruck3) (truck smallTruck4)
   (smallTruck smallTruck1) (smallTruck smallTruck2) (smallTruck smallTruck3) (smallTruck smallTruck4)

   ;; medium trucks
   (truck mediumTruck1) (truck mediumTruck2) (truck mediumTruck3) (truck mediumTruck4)
   (mediumTruck mediumTruck1) (mediumTruck mediumTruck2) (mediumTruck mediumTruck3) (mediumTruck mediumTruck4)

   ;; big trucks
   (truck bigTruck1) (truck bigTruck2) (truck bigTruck3) (truck bigTruck4)
   (bigTruck bigTruck1) (bigTruck bigTruck2) (bigTruck bigTruck3) (bigTruck bigTruck4)
   
   ;; Airplanes n boats n stuff!
   (airplane airplane1) 
   (boat boat1)

   ;; likewise, airports must be declared both as "location" and as
   ;; the subtype "airport",
   (location office1) (location office2) (location office3) (location office4)
   (location airport1) (location airport2)
   (location harbor1) (location harbor2) (location harbor3)
   (airport airport1) (airport airport2)
   (harbor harbor1) (harbor harbor2) (harbor harbor3)
   (city city1) (city city2) (city city3) (city city4)

   ;; "loc" defines the topology of the problem,
   (loc office1 city1) (loc office2 city2) (loc office3 city3) (loc office4 city4)
   (loc airport1 city1) (loc airport2 city2)
   (loc harbor1 city4) (loc harbor2 city2) (loc harbor3 city3)

   ;; the actual initial state of the problem, which specifies the
   ;; initial locations of all packages and all vehicles:
   
   (at packet1 office1)
   (at packet2 office3)
   ;; (at packet3 office4)
   (at mediumPacket1 office2)
   (at mediumPacket2 office1)
   (at bigPacket1 office4)
   (at bigPacket2 office3)

   (at smallTruck1 airport2)
   (at smallTruck2 airport1)
   (at smallTruck3 office3)
   (at smallTruck4 office4)
   (at mediumTruck1 office2)
   (at mediumTruck2 airport1)
   (at mediumTruck3 harbor1)
   (at mediumTruck4 office1)
   (at bigTruck1 office1)
   (at bigTruck2 office2)
   (at bigTruck3 office3)
   (at bigTruck4 office4)
   (at airplane1 airport1)
   (at boat1 harbor1)
   )

  ;; The goal is to have both packages delivered to their destinations:
  (:goal (and (at packet1 office2) (at packet2 office4) ;; (at packet3 office1) 
	      (at mediumPacket1 office1) (at mediumPacket2 office4)
	      (at bigPacket1 office3) (at bigPacket2 office1)
	 )
  )
)
