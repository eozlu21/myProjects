using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Pathfinder : MonoBehaviour
{

    [SerializeField] private WaveConfigSO waveConfig;

    private List<Transform> waypoints;

    private int waypointIndex = 0;
    // Start is called before the first frame update
    void Start()
    {
        waypoints = waveConfig.GetWaypoints();
        transform.position = waveConfig.GetStartingWaypoint().position;
    }

    // Update is called once per frame
    void Update()
    {
        FollowPath();
    }

    private void FollowPath()
    {
        if (waypointIndex >= waypoints.Count)
        {
            Destroy(gameObject);
            return;
        }

        Vector3 targetPos = waypoints[waypointIndex].position;
        float delta = waveConfig.GetMoveSpeed() * Time.deltaTime;
        transform.position = Vector2.MoveTowards(transform.position, targetPos, delta);
        if (transform.position.Equals(targetPos))
        {
            waypointIndex++;
        }
        
    }
}
