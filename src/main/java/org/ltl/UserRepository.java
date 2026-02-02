package org.ltl;

import org.microjvm.data.annotation.Repository;
import org.microjvm.data.jpa.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
