# Changelog

## 2.0.0-beta7
### Added
- Functionality for reading user input: `Terminal.readLineOrNull`, `Terminal.prompt` and various `Prompt` classes
- `TerminalRecorder` that saves output to memory rather than printing it.
- `TerminalRecorder.outputAsHtml()` that can render recorded output as an html file.
- 
### Changed
- When building tables, `borders` has been renamed `cellBorders`, and `outerBorder: Boolean` has been replaced with `tableBorders: Borders?`, which allows more control over the table's outside borders. [(#58)](https://github.com/ajalt/mordant/issues/58)
- Update Kotlin to 1.7.0

### Fixed
- Avoid clobbering output when using `Terminal.forStdErr` while an animation is running. [(#54)](https://github.com/ajalt/mordant/issues/54)

### Deprecated
- Deprecated the `VirtualTerminalInterface`. Use `TerminalRecorder` instead.

## 2.0.0-beta6
### Changed
- Update Kotlin to 1.6.20
- Publish JS target with the IR format in addition to LEGACY

### Fixed
- Fix race condition when using ProgressAnimation and adding interceptors in JVM [(#55)](https://github.com/ajalt/mordant/issues/55)

## 2.0.0-beta5
### Added
- Progress bars and other single-line animations are now supported in the IntelliJ console [(#49)](https://github.com/ajalt/mordant/issues/49)
- Added `bottomTitle` to `Panel`
- `Terminal.forStdErr` for printing to stderr rather than stdout
- Add `macosArm64` target for native M1 macs

### Changed
- Update Kotlin to 1.6.10
- *Breaking change*: Renamed `Table` and `Panel`'s `borderStyle` property to `borderType` and `borderTextStyle` to `borderStyle`
- *Breaking change*: Renamed `TerminalInfo`'s `stdinInteractive` and `stdoutInteractive` to `inputInteractive` and `outputInteractive`, respectively

### Fixed
- Fix regression in clearing animations [(#48)](https://github.com/ajalt/mordant/issues/48)

## 2.0.0-beta4
### Added
- `Spinner` widget that displays a looping animation
- `EmptyEidget` widget that can be used as a placeholder in layouts
- `row{}` and `column{}` widget layouts that create a single row/column of widgets

### Fixed
- Reduced flickering on high frame rate animations

## 2.0.0-beta3
### Changed
- Update Kotlin to 1.5.31
- Update Colormath to 3.0. If you use and colormath colors directly, you may need to update your imports.

### Fixed
- Fixed exception thrown when parsing markdown tables with empty cells
- Fixed rendering of markdown image reference links and link content

## 2.0.0-beta2
### Added
- Published artifacts for macOS

### Changed
- Update Kotlin to 1.5.10
- All text instances and print functions now default to preformatted whitespace, meaning that spaces and newlines will be preserved. You can explicitly pass `Whitespace.NORMAL` to restore the previous behavior.

## 2.0.0-beta1
### Added
- `Table.contentToCsv` to render a table's cells to csv format
- Added support for JavaScript and linux native targets 
- Getter properties for standard theme styles

### Changed
- Update Kotlin to 1.4.31
- Improve terminal capabilities detection

## 2.0.0-alpha2
### Added
- `Terminal.progressAnimation` builder to create a customizable progress bar animation
- Improved cursor APIs and added ability to produce cursor ANSI codes as a string
- Add ability to override detected terminal interactivity separately from the ANSI capabilities  [(#7)](https://github.com/ajalt/mordant/issues/7)

### Changed
- Rework theming system to simplify customization

## 2.0.0-alpha1
Mordant 2.0 is a rewrite that retains the simple APIs of Mordant 1.0, and adds support for rendering
complex widgets.

### Added
- Added renderable widgets, including tables, panels, and lists
- Added markdown rendering
- Added a theme system to customize text styles on an entire terminal instance
- Added animations that automatically clear the previous frame when redrawing

### Changed
- Improved terminal capability detection
- ANSI colors and styles can now be applied through the `TextColors` and `TextStyles` top-level
  objects, and `Terminal.print` will downsample th resulting strings based on the detected terminal
  capabilities.

## 1.2.1
### Changed
- Improve support for color detection in IntelliJ and VS Code terminals

## 1.2.0
### Added
- Add functions for generating ANSI cursor movement
- Add ability to generate ANSI color codes from any colormath color object
- Update colormath to 1.2.0

## 1.1.0
### Added
- Add support for XYZ and LAB color spaces

## 1.0.0
- Initial Release
