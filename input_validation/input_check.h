#pragma once
#include <vector>
#include <unordered_map>
#include <string>
#include <variant>
#include <optional>


#define GET_INT(r, n) std::get<int>(r[n].second)
#define GET_FLOAT(r, n) std::get<float>(r[n].second)
#define GET_STRING(r, n) std::get<std::string>(r[n].second)


enum var_types { INT = 'i', FLOAT = 'f', STRING = 's' };

typedef std::vector<std::pair<var_types, std::variant<int, float, std::string>>> parse_result;

struct func {
	std::vector<var_types> arguments;
};

class Input
{
public:
	Input() = default;
	~Input() = default;

	
	bool add_function(const std::string&, const func&);
	bool delete_function(const std::string&);
	bool check_valid(const std::string&, const std::string&) const;
	
	std::optional<parse_result> parse(const std::string&, const std::string&) const;

private:
	bool is_int(const std::string&) const;

	bool is_float(const std::string&) const;

	bool is_string(const std::string&) const;

	bool special_char_is_allowed(const char&) const;

private:
	std::unordered_map<std::string, func> m_functions;
	const char* m_chars_allowed = " !?";
};
