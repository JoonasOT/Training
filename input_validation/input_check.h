#pragma once
#include <vector>
#include <unordered_map>
#include <string>
#include <variant>
#include <optional>

/*  Macros for reducing code in main  */
#define GET_INT(parse_res, n)	std::get<int>(parse_res[n].second)
#define GET_FLOAT(parse_res, n) std::get<float>(parse_res[n].second)
#define GET_STRING(parse_res, n) std::get<std::string>(parse_res[n].second)
#define GET_PARSE_TYPE(parse_res, n) parse_res[n].first

/* Possible data types of input */
enum var_types { INT, FLOAT, STRING };

/* Type of parse result */
typedef std::vector<std::pair<var_types, std::variant<int, float, std::string>>> parse_result;

/* Struct for holding onto function data */
struct func {
	std::vector<var_types> arguments;
};

/* Get nth data field of parse result */
template<class T>
inline T GET_(const parse_result& pr, const unsigned int& nth_field) {
	return std::get<T>(pr[nth_field].second);
}

/* Class for handling input validation */
class Input
{
public:
	Input() = default;
	~Input() = default;

	// Adding and deleting functions
	bool add_function(const std::string& func_name, const func& function);
	bool delete_function(const std::string& func_name); /* I guess u might use this for debugging?? */
	
	// Check if given user input follows the given format and restrictions
	bool check_valid(const std::string&, const std::string&) const;
	
	// Checking user input and returning params. Returns empty optional on invalid params
	std::optional<parse_result> parse(const std::string& func_name, const std::string& user_args) const;

private:
	// Funcs for checking if given string is of certain type
	bool is_int(const std::string&) const;
	bool is_float(const std::string&) const;
	bool is_string(const std::string&) const;

	// Small function to check if special chars in string is allowed
	bool special_char_is_allowed(const char&) const;

private:
	// All the functions and their formats
	std::unordered_map<std::string, func> m_functions;

	// Chars that ARE allowed in user input
	const char* m_chars_allowed = " !?";
};
