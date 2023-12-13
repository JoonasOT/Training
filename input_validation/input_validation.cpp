#include <iostream>

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
    if (!x.has_value()) {
        std::cout << "No value!\n";
        return 1;
    }

    std::cout << "Calling print()\n";
    parse_result& r = x.value();
    print(GET_INT(r, 0), GET_FLOAT(r, 1), GET_STRING(r, 2));

    auto y = in.parse("test2", "string1, 2, 1, 15.02, 3, string2");
    r = y.value();

    std::cout << "\n\n\n\n";

    std::cout << "---Custom out---\n";
    for (unsigned int i = 0; i < r.size(); i++) {
        switch (r[i].first)
        {
        case INT:
            std::cout << "(INT) -> " << GET_INT(r, i) << "\n";
            break;
        case FLOAT:
            std::cout << "(FLOAT) -> " << GET_FLOAT(r, i) << "\n";
            break;
        case STRING:
            std::cout << "(STRING) -> " << GET_STRING(r, i) << "\n";
            break;
        }
    }
    return 0;
}