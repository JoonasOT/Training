#include <vector>

#pragma once
class statistics
{
public:

private:
	std::vector<double> m_power = {};
	std::vector<double> m_hr = {};
	double m_avg_power;
	double m_np_power;
	double m_avg_hr;
	double m_tss;
};

