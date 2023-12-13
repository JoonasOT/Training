#include <iostream>

#include "metrics.h"

int main()
{
    std::cout << Metrics::calculate_FTP(210, Metrics::FTP_TEST_TYPE::HOUR);
}
