#include <iostream>
#include <iomanip>

#include "input_check.h"

void print(int i, float f, std::string s) {
    std::cout << "Print [int, float, string]: " << i << ", " << f << " & " << s << "\n";
}


int main()
{
    Input in = Input();
    in.add_function("test", func{ {INT, FLOAT, STRING} });
    in.add_function("test2", func{ {STRING, INT, INT, FLOAT, INT, STRING} });

    std::cout << "---Validation---\n";
    std::cout << "Is function 'test' valid with ('1', '1.0', 'test')? -> " << in.check_valid("test", "1, 1.0, test") << "\n";
    std::cout << "Is function 'test' valid with ('xd', '1.0', 'test')? -> " << in.check_valid("test", "xd, 1.0, test") << "\n";
    std::cout << "\n\n\n\n";

    std::cout << "----PARSING-----\n";
    auto x = in.parse("test", "1, 1.2, test");
    
    // Check for value
    if (!x.has_value()) return 1;

    std::cout << "Calling print()\n";
    parse_result& r = x.value();
    print(GET_<int>(r, 0), GET_<float>(r, 1), GET_<std::string>(r, 2));

    auto y = in.parse("test2", "string1, 2, 1, 15.02, 3, string2");
    r = y.value();

    std::cout << "\n\n\n\n";

    std::cout << "---Custom out---\n";
    for (unsigned int i = 0; i < r.size(); i++) {
        switch (GET_PARSE_TYPE(r, i)) {
        case INT:
            std::cout << std::setw(12) << "(INT) -> "   << GET_<int>(r, i) << "\n";
            break;
        case FLOAT:
            std::cout << std::setw(12) << "(FLOAT) -> " << GET_<float>(r, i) << "\n";
            break;
        case STRING:
            std::cout << std::setw(12) << "(STRING) -> "<< GET_<std::string>(r, i) << "\n";
            break;
        }
    }
    return 0;
}