# Elevator Design


## Key classes
### 
- elevators: List < Elevator >

### Elevator
- currentFloor
- direction : UP, DOWN
- isIdle: boolean
- requests

### Request
- floor: int
- direction: UP, DOWN
### InternalRequest
- toFloor: int

## Interfaces
### ElevatorGroupController
- submitRequest(Request): void
### ElevatorManager
- isMoving(Elevator): boolean
- selectFloor(e: Elevator, ir: InternalRequest): void
- 
### ElevatorSelectionStrategy
- selectElevatorForRequest(ElevatorSystem eg, Request r): Optional< Elevator >


