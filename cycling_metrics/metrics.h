#pragma once

#include <vector>

typedef long double watt;
typedef float KJ;
typedef float kcal;
typedef float seconds;

namespace Metrics {
	enum FTP_TEST_TYPE { RAMP = 75, HOUR = 100, TWENTY = 95 };
	static inline watt calculate_AVG_power(const std::vector<watt>& power);
	static inline watt calculate_FTP(const watt& power, const FTP_TEST_TYPE& t = RAMP);
	static inline watt calculate_NP(const std::vector<watt>& power, const seconds& dt = 1);
	static inline float calculate_IF(const watt& normalized_power, const watt& functional_threshold_power);
	static inline watt calculate_TSS(const watt& normalized_power, const watt& functional_threshold_power, const float& intensity_factor, const seconds& sec);
	static inline KJ calculate_energy(const watt& power, const seconds& sec);
	static inline kcal convert_KJ_kcal(const KJ& energy);
	static inline kcal convert_kcal_KJ(const kcal& calories);
}

