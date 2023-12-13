#include <iostream>

#include "input_check.h"

int main()
{
    Input in = Input();
    in.add_function("test", func{ {INT, FLOAT, STRING} });
    std::cout << "Test: " << in.check_valid("test", "1, 1.0, ///") << "\n";
}