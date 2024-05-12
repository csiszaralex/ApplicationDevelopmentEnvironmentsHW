# Additional clean files
cmake_minimum_required(VERSION 3.16)

if("${CONFIG}" STREQUAL "" OR "${CONFIG}" STREQUAL "Debug")
  file(REMOVE_RECURSE
  "CMakeFiles\\appBidder_autogen.dir\\AutogenUsed.txt"
  "CMakeFiles\\appBidder_autogen.dir\\ParseCache.txt"
  "appBidder_autogen"
  )
endif()
