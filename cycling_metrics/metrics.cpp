#include "metrics.h"


namespace Metrics {
	static inline watt calculate_AVG_power(const std::vector<watt>& p) {
		watt res = 0;
		for (const auto& e : p) res += e;
		return ( res / p.size() );
	}

	static inline watt calculate_FTP(const watt& p, const FTP_TEST_TYPE& t) {
		return (float)t / 100.0f * p;
	}

	static inline watt calculate_NP(const std::vector<watt>& p, const seconds& dt) {
		if ( p.empty() ) return 0;
		watt power = 0;
		if (dt * p.size() <= 30.0f) {
			for (const auto& e : p) power += e;
			return power / p.size();
		}

		std::vector<watt> tmp = p;

		for (unsigned int i = 0; i < p.size(); i++) {
			tmp[i] = 0;
			for (int o = -15.0f / dt; o < 15.0f / dt; o++) {
				if (i + o < 0) continue;
				if (i + o >= p.size()) continue;
				tmp[i] += p[i + o];
			}
			tmp[i] *= tmp[i];
			tmp[i] *= tmp[i];
			power += tmp[i];
		}
		power /= p.size();
		power = sqrt(power);
		return sqrt(power);
	}

	static inline float calculate_IF(const watt& np, const watt& ftp) {
		return np / ftp;
	}

	static inline watt calculate_TSS(const watt& np, const watt& ftp, const float& IF, const seconds& s) {
		return (s * np * IF) / (ftp * 3600) * 100;
	}

	static inline KJ calculate_energy(const watt& p, const seconds& s) {
		return p * s;
	}

	static inline kcal convert_KJ_kcal(const KJ& e) {
		return e/4.148f;
	}
	static inline kcal convert_kcal_KJ(const kcal& e) {
		return e * 4.148f;
	}
}