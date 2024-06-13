package com.lps.ldtracker.configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface RealSessionAware {

	default Session getRealSession(SessionFactory sF) {
		try {
			return sF.getCurrentSession();
		} catch (Exception e) {
			return sF.openSession();
		}
	}
}