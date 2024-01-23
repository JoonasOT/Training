#pragma once
#include <ctime>
#include <map>

#include "statistics.h"

enum ACTIVITY_TYPE {
	RIDE, RUN, SWIM, SAUNA
};

typedef time_t time_date;

class activity
{
public:
	activity();
	std::pair<time_date, time_date> get_times();
	ACTIVITY_TYPE get_type();

private:
	time_date m_start_time;
	time_date m_end_time;
	ACTIVITY_TYPE m_type;
	statistics m_stats;
};

